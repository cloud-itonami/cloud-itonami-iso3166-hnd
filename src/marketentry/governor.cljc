(ns marketentry.governor
  "Market-Entry Compliance Governor -- the independent compliance layer
  that earns the MarketEntry-LLM the right to commit. The LLM has no
  notion of Honduran procurement law, whether the SPECIFIC Cámara de
  Comercio an engagement names actually has territorial jurisdiction
  over that engagement's own declared business domicile (Honduras's
  Registro Público de Comercio is split department-by-department, not
  one national registry), whether a claimed engagement fee actually
  equals base + months x rate, whether the required registration
  evidence has actually been assessed for a filing, or when a draft
  stops being a draft and becomes a real-world HonduCompras portal
  submission, so this MUST be a separate system able to *reject* a
  proposal and fall back to HOLD.

  `:itonami.blueprint/governor` is `:market-entry-compliance-governor`
  (shared family keyword on blueprints; this is the Honduras running
  implementation of that governor for the iso3166 family, following
  the AGO template's structure).

  This blueprint's own text (README Core Contract: 'No automated
  proposal can submit a portal registration or filing the governor
  refuses, suppress a compliance record, or claim a legal/tax
  conclusion the governor has not cleared') names exactly the checks
  below.

  Checks, in priority order, ALL HARD violations: a human approver
  CANNOT override them. The confidence/actuation gate is SOFT: it
  asks a human to look (low confidence / actuation), and the human
  may approve -- but see `marketentry.phase`: for `:stake
  :actuation/draft-filing`/`:actuation/submit-filing` NO phase ever
  allows auto-commit either. Two independent layers agree that
  actuation is always a human call.

    1. Spec-basis                  -- did the jurisdiction proposal cite
                                       an OFFICIAL source
                                       (`marketentry.facts`), or invent
                                       one?
    2. Evidence incomplete         -- for `:filing/draft`/
                                       `:filing/submit`, has the
                                       jurisdiction actually been
                                       assessed with a full evidence
                                       checklist on file? For Honduras
                                       this covers: territorial
                                       Cámara de Comercio Registro
                                       Mercantil inscription, SAR RTN,
                                       SAR fiscal-solvency, ONCAE
                                       Registro de Proveedores y
                                       Contratistas, and a municipal
                                       operating permit -- five
                                       independently-sourced
                                       required-evidence items, none
                                       fabricated.
    3. Cámara jurisdiction mismatch -- for `:filing/submit`,
                                       INDEPENDENTLY verify (never
                                       trust the engagement's own
                                       self-report) whether the
                                       engagement's own claimed
                                       `:registering-camara` actually
                                       has CONFIRMED territorial
                                       jurisdiction over its own
                                       claimed
                                       `:business-domicile-department`.
                                       FLAGSHIP genuinely new check
                                       for the iso3166 family
                                       (grep-verified absent as a
                                       governor check function name
                                       fleet-wide at build time): a
                                       MULTI-AUTHORITY
                                       TERRITORIAL-JURISDICTION
                                       CONSISTENCY test, grounded in
                                       Honduras's Registro Público de
                                       Comercio being split
                                       department-by-department
                                       (Código de Comercio, Decreto
                                       N.º 73-50, Arts. 384-386, 420 --
                                       the SAME decree already cited
                                       in this repo's own
                                       `statute.facts/catalog`).
                                       DOMICILE-CONDITIONAL, not a
                                       blanket rule -- it must fire
                                       ONLY when the claimed chamber
                                       does not actually cover the
                                       claimed domicile department
                                       (including when the chamber is
                                       not one of the two this
                                       catalog independently
                                       confirmed at all), never for a
                                       domicile/chamber pair this
                                       catalog has confirmed to match.
    4. Engagement fee mismatch     -- for `:filing/submit`,
                                       INDEPENDENTLY recompute whether
                                       the engagement's own `:claimed-
                                       fee` equals `base-fee +
                                       monthly-rate x monitoring-
                                       months` -- honest reapplication
                                       of the ground-truth-recompute
                                       discipline sibling actors use.
    5. Confidence floor / actuation
       gate                          -- LLM confidence below threshold,
                                       OR the op is `:filing/draft`/
                                       `:filing/submit` (REAL acts)
                                       -> escalate.

  Two more guards, double-draft/double-submit prevention, are enforced
  off dedicated `:drafted?`/`:submitted?` facts (never a `:status`
  value)."
  (:require [marketentry.facts :as facts]
            [marketentry.registry :as registry]
            [marketentry.store :as store]))

(def confidence-floor 0.6)

(def high-stakes
  "Stakes grave enough to always require a human, even when clean.
  Drafting a real HonduCompras filing package and submitting a real
  filing / Registro de Proveedores y Contratistas inscription request
  are the two real-world actuation events this actor performs."
  #{:actuation/draft-filing :actuation/submit-filing})

;; ----------------------------- checks -----------------------------

(defn- spec-basis-violations
  "A `:jurisdiction/assess` (or `:filing/draft`/`:filing/submit`)
  proposal with no spec-basis citation is a HARD violation -- never
  invent a jurisdiction's market-entry requirements."
  [{:keys [op]} proposal]
  (when (contains? #{:jurisdiction/assess :filing/draft :filing/submit} op)
    (let [value (:value proposal)]
      (when (or (empty? (:cites proposal))
                (and (contains? value :spec-basis) (nil? (:spec-basis value))))
        [{:rule :no-spec-basis
          :detail "公式spec-basisの引用が無い提案は法域要件として扱えない"}]))))

(defn- evidence-incomplete-violations
  "For `:filing/draft`/`:filing/submit`, the jurisdiction's required
  registration evidence (Cámara de Comercio Registro Mercantil, SAR
  RTN, SAR solvencia fiscal, ONCAE Registro de Proveedores,
  permiso de operación municipal) must actually be satisfied."
  [{:keys [op subject]} st]
  (when (contains? #{:filing/draft :filing/submit} op)
    (let [e (store/engagement st subject)
          assessment (store/assessment-of st subject)]
      (when-not (and assessment
                     (facts/required-evidence-satisfied?
                      (:jurisdiction e) (:checklist assessment)))
        [{:rule :evidence-incomplete
          :detail "法域の必要書類(Registro Mercantil/RTN/Solvencia Fiscal/ONCAE登録/市の営業許可等)が充足していない状態での提案"}]))))

(defn- camara-jurisdiction-mismatch-violations
  "For `:filing/submit`, INDEPENDENTLY verify whether the engagement's
  own claimed `:registering-camara` actually has confirmed
  territorial jurisdiction over its own claimed
  `:business-domicile-department` -- the flagship check this vertical
  adds. Honduras's Registro Público de Comercio is split
  department-by-department (Código de Comercio Arts. 384-386, 420),
  never a single national registry -- fires ONLY when the claimed
  chamber does not actually cover the claimed domicile."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when (registry/camara-jurisdiction-mismatch? e)
        [{:rule :camara-jurisdiction-mismatch
          :detail (str subject " は登録商工会議所(" (:registering-camara e)
                      ")の管轄が事業所在地(" (:business-domicile-department e)
                      ")をカバーしていない -- ホンジュラスの商業登記簿は県ごとに分割されており"
                      "(Código de Comercio Decreto 73-50 Arts.384-386,420)、"
                      "誤った商工会議所での登記は有効な登記とならない")}]))))

(defn- engagement-fee-mismatch-violations
  "For `:filing/submit`, INDEPENDENTLY recompute whether the
  engagement's own claimed fee equals base + months x rate."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when-not (registry/engagement-fee-matches-claim? e)
        [{:rule :engagement-fee-mismatch
          :detail (str subject " の申告手数料(" (:claimed-fee e)
                      ")が独立再計算値(" (registry/compute-engagement-fee e) ")と一致しない")}]))))

(defn- already-drafted-violations
  "For `:filing/draft`, refuses to draft the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/draft)
    (when (store/engagement-already-drafted? st subject)
      [{:rule :already-drafted
        :detail (str subject " は既にドラフト済み")}])))

(defn- already-submitted-violations
  "For `:filing/submit`, refuses to submit the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (when (store/engagement-already-submitted? st subject)
      [{:rule :already-submitted
        :detail (str subject " は既に提出済み")}])))

(defn check
  "Censors a MarketEntry-LLM proposal against the governor rules.
  Returns {:ok? bool :violations [..] :confidence c :escalate? bool
  :high-stakes? bool :hard? bool}."
  [request _context proposal st]
  (let [hard (into []
                   (concat (spec-basis-violations request proposal)
                           (evidence-incomplete-violations request st)
                           (camara-jurisdiction-mismatch-violations request st)
                           (engagement-fee-mismatch-violations request st)
                           (already-drafted-violations request st)
                           (already-submitted-violations request st)))
        conf (:confidence proposal 0.0)
        low? (< conf confidence-floor)
        stakes? (boolean (high-stakes (:stake proposal)))
        hard? (boolean (seq hard))]
    {:ok?          (and (not hard?) (not low?) (not stakes?))
     :violations   hard
     :confidence   conf
     :hard?        hard?
     :escalate?    (and (not hard?) (or low? stakes?))
     :high-stakes? stakes?}))

(defn hold-fact
  "The audit fact written when a proposal is rejected (HOLD)."
  [request context verdict]
  {:t          :governor-hold
   :op         (:op request)
   :actor      (:actor-id context)
   :subject    (:subject request)
   :disposition :hold
   :basis      (mapv :rule (:violations verdict))
   :violations (:violations verdict)
   :confidence (:confidence verdict)})
