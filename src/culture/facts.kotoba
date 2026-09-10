(ns culture.facts
  "Country-level regional-culture catalog for Honduras (HND) -- national
  dishes, protected products, beverages, crafts, festivals and heritage
  sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"HND"
   [{:culture/id "hnd.dish.baleada"
     :culture/name "Baleada"
     :culture/country "HND"
     :culture/kind :dish
     :culture/summary "Traditional Honduran dish originating from the northern coast, particularly La Ceiba: a flour tortilla filled with a smear of mashed refried red beans, crema and crumbled queso duro."
     :culture/url "https://en.wikipedia.org/wiki/Baleada"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "hnd.dish.sopa-de-caracol"
     :culture/name "Sopa de caracol"
     :culture/country "HND"
     :culture/kind :dish
     :culture/summary "Honduran conch soup in which the conch is cooked in coconut milk and the conch's broth, with spices, yuca, cilantro and green bananas known as guineo verde."
     :culture/url "https://en.wikipedia.org/wiki/Honduran_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "hnd.dish.carneada"
     :culture/name "Carneada"
     :culture/country "HND"
     :culture/kind :dish
     :culture/summary "One of Honduras' national dishes: grilled beef marinated in sour orange juice and spices, served with chismol salsa and plantains."
     :culture/url "https://en.wikipedia.org/wiki/Honduran_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "hnd.dish.sopa-de-frijoles"
     :culture/name "Sopa de frijoles"
     :culture/country "HND"
     :culture/kind :dish
     :culture/summary "Traditional Honduran bean soup of black or red beans boiled with garlic, blended and combined with pork-bone broth."
     :culture/url "https://en.wikipedia.org/wiki/Honduran_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "hnd.product.coffee"
     :culture/name "Honduran coffee"
     :culture/country "HND"
     :culture/kind :product
     :culture/summary "Honduras transitioned from minimal coffee exportation in the 1890s to becoming Central America's top coffee producer in 2011."
     :culture/url "https://en.wikipedia.org/wiki/Coffee_production_in_Honduras"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "hnd.festival.la-ceiba-carnival"
     :culture/name "La Ceiba Carnival"
     :culture/country "HND"
     :culture/kind :festival
     :culture/summary "Annual celebration held in La Ceiba, Honduras, on the third or fourth Saturday of May, honouring Saint Isidore the Laborer with parades and neighbourhood festivities attracting over 500,000 visitors."
     :culture/url "https://en.wikipedia.org/wiki/La_Ceiba_Carnival"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "hnd.heritage.copan"
     :culture/name "Copán"
     :culture/country "HND"
     :culture/kind :heritage
     :culture/summary "Archaeological site of the Maya civilisation in western Honduras near the Guatemala border, designated a UNESCO World Heritage Site in 1980."
     :culture/url "https://en.wikipedia.org/wiki/Cop%C3%A1n"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "hnd.heritage.rio-platano"
     :culture/name "Río Plátano Biosphere Reserve"
     :culture/country "HND"
     :culture/kind :heritage
     :culture/summary "Protected tropical rainforest area in northeastern Honduras covering 5,250 square kilometres, designated a UNESCO World Heritage Site in 1982."
     :culture/url "https://en.wikipedia.org/wiki/R%C3%ADo_Pl%C3%A1tano_Biosphere_Reserve"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

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
      :note (str "cloud-itonami-iso3166-hnd culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "HND"))
                 " HND entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
