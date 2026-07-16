(ns statute.facts
  "General-law compliance catalog for Honduras (HND). Like
  cloud-itonami-iso3166-ury/-cri/-pan/-ecu/-pry/-gtm, this repo had no
  `marketentry.facts` implementation yet (blueprint-only) -- this is
  the FIRST code-bearing content in this repo, self-contained with its
  own deps.edn. Mirrors
  cloud-itonami-iso3166-jpn/-usa/-esp/-swe/-nor/-dnk/-fin/-prt/-bel/-bra/-mex/-chl/-arg/-zaf/-col/-ury/-cri/-pan/-ecu/-pry/-gtm's
  `statute.facts` (ADR-2607141700, cloud-itonami-compliance-fact-federation).

  The Commercial Code entry's own BCH (Banco Central de Honduras)
  mirror rendered as an illegible font-subsetted PDF (only the
  national coat of arms was recognizable). WIPO Lex's mirror of the
  same decree rendered fully legibly instead and was used: title
  \"Código de Comercio (aprobado por Decreto N.º 73-50)\", with the
  document's own header text directly confirming a promulgation date
  of 1 de mayo de 1950 (distinct from a secondary RAE citation that
  gives Feb 17 1950 as the decree's signing date -- the WIPO Lex
  primary-source PDF's own promulgation-date text was preferred).

  The Access to Public Information Law entry was directly confirmed
  from the Tribunal Superior de Cuentas (TSC, Honduras's official
  government auditing institution) mirror, whose first page legibly
  shows the \"Diario Oficial La Gaceta, 30 de diciembre de 2006\"
  dateline and \"Decreto Legislativo No. 170 - 2006\" heading.

  A law not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of statute entries."
  {"HND"
   [{:statute/id "hnd.codigo-comercio-decreto-73-50"
     :statute/title "Código de Comercio (Decreto N.º 73-50)"
     :statute/jurisdiction "HND"
     :statute/kind :law
     :statute/law-number "Decreto N.º 73-50"
     :statute/url "https://www.wipo.int/wipolex/edocs/lexdocs/laws/es/hn/hn009es.pdf"
     :statute/url-provenance :official-wipo-lex-mirror
     :statute/enacted-date "1950-05-01"
     :statute/retrieved-at "2026-07-16"
     :statute/topic #{:corporate-governance :incorporation}}
    {:statute/id "hnd.ley-transparencia-acceso-informacion-publica-decreto-170-2006"
     :statute/title "Ley de Transparencia y Acceso a la Información Pública (Decreto Legislativo N.º 170-2006)"
     :statute/jurisdiction "HND"
     :statute/kind :law
     :statute/law-number "Decreto Legislativo N.º 170-2006"
     :statute/url "https://www.tsc.gob.hn/web/leyes/Ley_de_Transparencia.pdf"
     :statute/url-provenance :official-tsc-gob-hn
     :statute/enacted-date "2006-12-30"
     :statute/retrieved-at "2026-07-16"
     :statute/topic #{:information-disclosure :transparency}}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-hnd statute.facts Wave 0 (ADR-2607141700): "
                 (count (get catalog "HND")) " HND statutes seeded with "
                 "official WIPO Lex/TSC citations. Extend "
                 "`statute.facts/catalog`, never fabricate a law-id or URL.")})))

(defn by-topic [iso3 topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis iso3)))
