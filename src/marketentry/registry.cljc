(ns marketentry.registry
  "Pure-function market-entry filing-draft + filing-submit record
  construction -- an append-only market-entry book-of-record draft.

  Like every sibling actor's registry, there is no single international
  reference-number standard for a public-procurement market-entry
  filing -- Honduras assigns its own formats (ONCAE Registro de
  Proveedores y Contratistas numbers, SAR RTN numbers, and each
  departmental Cámara de Comercio's own Registro Mercantil folio
  numbers each have their own real formats this actor does not
  reproduce). This namespace does NOT invent one; it builds a
  jurisdiction-scoped sequence number and validates the record's
  required fields, the same honest, non-fabricating discipline
  `marketentry.facts` uses.

  `engagement-fee-matches-claim?` is an HONEST reapplication of the
  SAME ground-truth-recompute DISCIPLINE sibling actors use (verify a
  claimed monetary total against the entity's own recorded quantity x
  unit fields), reapplied to a market-entry engagement fee line.

  `camara-jurisdiction-mismatch?` is the FLAGSHIP check for this
  vertical -- see `marketentry.facts`'s namespace docstring for the
  full grounding (Código de Comercio Decreto N.º 73-50 Arts. 384-386,
  420, independently confirmed against two live departmental Cámara
  de Comercio websites). It INDEPENDENTLY verifies whether the
  engagement's own claimed `:registering-camara` actually has
  confirmed territorial jurisdiction over its own claimed
  `:business-domicile-department` -- a MULTI-AUTHORITY
  TERRITORIAL-JURISDICTION CONSISTENCY check, not a boolean-presence
  check, not a monetary-threshold check, not a sector-conditional
  restriction.

  This namespace is pure data + pure functions -- no I/O, no network
  call to any real procurement portal (HonduCompras included). It
  builds the RECORD an operator would keep, not the act of submitting
  a portal registration itself (that is `marketentry.operation`'s
  `:filing/submit`, always human-gated -- see README Actuation)."
  (:require [clojure.string :as str]
            [marketentry.facts :as facts]))

(defn- unsigned-certificate
  "Every certificate this actor produces is UNSIGNED -- signature is
  the market-entry operator's act, not this actor's."
  [kind subject record-id]
  {"@context" ["https://www.w3.org/ns/credentials/v2"]
   "type" ["VerifiableCredential" kind]
   "credentialSubject" {"id" subject "record" record-id}
   "proof" nil
   "issued_by_registry" false
   "status" "draft-unsigned"})

(defn- zero-pad [n w]
  (let [s (str n)]
    (str (apply str (repeat (max 0 (- w (count s))) "0")) s)))

(defn compute-engagement-fee
  "The ground-truth engagement fee for `engagement`'s own `:base-fee`
  and `:monitoring-months` x `:monthly-rate` -- a single flat
  base + months x rate calculation, not a full pricing engine."
  [{:keys [base-fee monthly-rate monitoring-months]}]
  (+ (double base-fee)
     (* (double monthly-rate) (double monitoring-months))))

(defn engagement-fee-matches-claim?
  "Does `engagement`'s own `:claimed-fee` equal the independently
  recomputed `compute-engagement-fee`?"
  [{:keys [claimed-fee] :as engagement}]
  (== (double claimed-fee) (compute-engagement-fee engagement)))

(defn camara-jurisdiction-mismatch?
  "FLAGSHIP check. INDEPENDENTLY verifies whether `engagement`'s own
  claimed `:registering-camara` (e.g. \"CCIT\") actually has
  CONFIRMED territorial jurisdiction (per
  `marketentry.facts/camara-covers-department?`) over its own claimed
  `:business-domicile-department` (e.g. \"Cortés\"). Returns true
  (mismatch -- a violation) when the claimed chamber does NOT cover
  the claimed domicile, including when the chamber is not one of the
  two this catalog independently confirmed at all -- never silently
  assume jurisdiction for an unverified chamber."
  [{:keys [jurisdiction registering-camara business-domicile-department]}]
  (not (facts/camara-covers-department? jurisdiction registering-camara business-domicile-department)))

(defn register-draft
  "Validate + construct the FILING-DRAFT registration DRAFT -- the
  market-entry operator's own act of preparing a portal registration
  package. Pure function -- does not touch any real procurement
  portal (HonduCompras included)."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "draft: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "draft: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "draft: sequence must be >= 0" {})))
  (let [draft-number (str (str/upper-case jurisdiction) "-DFT-" (zero-pad sequence 6))
        record {"record_id" draft-number
                "kind" "filing-draft"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "draft_number" draft-number
     "certificate" (unsigned-certificate "FilingDraft" draft-number draft-number)}))

(defn register-submit
  "Validate + construct the FILING-SUBMIT registration DRAFT -- the
  market-entry operator's own act of actually submitting a portal
  registration (always human-gated upstream)."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "submit: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "submit: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "submit: sequence must be >= 0" {})))
  (let [submit-number (str (str/upper-case jurisdiction) "-SUB-" (zero-pad sequence 6))
        record {"record_id" submit-number
                "kind" "filing-submit"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "submit_number" submit-number
     "certificate" (unsigned-certificate "FilingSubmit" submit-number submit-number)}))

(defn append [history result]
  (conj (vec history) (get result "record")))
