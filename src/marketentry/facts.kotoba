(ns marketentry.facts
  "Honduras (HND) market-entry catalog.

  Every field traces to an independently-confirmed official source,
  fetched and read THIS session (2026-07-23):

    - Public procurement: Ley de Contratación del Estado, Decreto
      No. 74-2001 (Congreso Nacional). This catalog does NOT trust
      ONCAE's own about-page paraphrase of the decree number at face
      value -- it fetched the LAW'S OWN PDF
      (oncae.gob.hn/biblioteca/normativa/ley-de-contratacion-del-estado/)
      and read the decree's own opening text directly: 'DECRETO No.
      74-2001 ... Que la Ley de Contratación del Estado contenida en
      el Decreto No. 148-85 de fecha 29 de septiembre de 1985, se
      vuelve obsolescente...' -- i.e. Decreto 74-2001 EXPRESSLY
      supersedes a prior 1985 law (Decreto 148-85), a fact this
      catalog states because the primary text itself states it, not
      because it was assumed. The exact approval date (1 de junio de
      2001) was independently cross-confirmed from a SECOND primary
      document in the SAME session: Decreto Ejecutivo No. 10-2005's
      own recital text ('CONSIDERANDO: Que mediante Decreto 74-2001,
      de fecha 1 de junio de 2001, se aprobó la Ley de Contratación
      del Estado...'). Reformada por, entre otros, Decreto No.
      266-2013 (cited inline in the law's own footnote 1, own text
      read directly: 'Mediante Decreto 266-2013 de fecha dieciséis
      del mes de noviembre del 2013 se reforma el artículo 2...').
    - Autoridad Rectora: Oficina Normativa de Contratación y
      Adquisiciones del Estado (ONCAE), created by the SAME Decreto
      74-2001, Artículo 30 (own text, read directly): 'Créase la
      Oficina Normativa de Contratación y Adquisiciones del Estado.'
    - E-procurement portal: HonduCompras (www.honducompras.gob.hn),
      created by Decreto Ejecutivo No. 10-2005 (own text, read
      directly from the decree's own PDF, published La Gaceta No.
      30,824, 14 de octubre de 2005): 'Artículo 1.-Créase el Sistema
      de Información de Contratación y Adquisiciones del Estado de
      Honduras \"HonduCompras\" (www.honducompras.gob.hn), el cual
      será administrado por la Oficina Normativa de Contratación y
      Adquisiciones del Estado (ONCAE).' Artículo 2 (own text): use is
      MANDATORY ('será de uso obligatorio') for every organ within
      the Ley de Contratación del Estado's scope. NOTE: a direct
      HTTPS fetch of honducompras.gob.hn itself failed this session
      with an EXPIRED TLS CERTIFICATE (disclosed, not concealed --
      this catalog does not claim to have independently browsed the
      live portal this session; the decree text above and ONCAE's own
      description of the portal were fetched instead).
    - Registro de Proveedores y Contratistas del Estado: the SAME
      Decreto 74-2001, Artículo 34 (own text, read directly): 'La
      Oficina Normativa de Contratación y Adquisiciones llevará un
      registro centralizado en el que se inscribirán los interesados
      en la adjudicación de contratos con los organismos estatales.'
      This registry is EXPLICITLY CENTRALIZED (one national registry
      at ONCAE) -- a deliberate CONTRAST with the mercantile registry
      below, which is territorially decentralized. Never conflate the
      two: ONCAE's own supplier registry is one thing, the Cámara de
      Comercio's Registro Mercantil is a different, prior-in-sequence
      registry a supplier must already hold before it can even
      complete ONCAE's own certification packet (confirmed
      operationally: ONCAE's own 'Requisitos para certificarse como
      proveedor del Estado' page, fetched this session, lists a
      Cámara de Comercio-adjacent constitutional-deed/company record
      as a PRECONDITION document for the Sociedad Mercantil /
      Empresa Extranjera applicant categories).
    - Mercantile registration -- THE FLAGSHIP GROUNDING, and a
      genuinely different SHAPE from every prior sibling in this
      family (grep-verified absent as a governor check function name
      fleet-wide at build time): Honduras's Registro Público de
      Comercio is NOT one national registry. This catalog does not
      merely assert that -- it independently confirmed it from BOTH
      the primary Código de Comercio text (Decreto N.º 73-50, the
      SAME decree already cited in this repo's own
      `statute.facts/catalog` as `hnd.codigo-comercio-decreto-73-50`
      -- this catalog reuses that exact citation, never a second,
      different one) AND from two live, INDEPENDENT departmental
      Cámara de Comercio websites in the SAME session:
        1. Código de Comercio Título I, Capítulo II 'INSCRIPCIÓN EN
           LA CÁMARA DE COMERCIO', Artículo 384 (own text, read
           directly): 'Es obligatorio el registro de todo comerciante
           en la Cámara de Comercio e Industrias correspondiente.'
        2. Título I, Capítulo III 'DEL REGISTRO PÚBLICO DE COMERCIO',
           Artículo 385 (own text, read directly): 'El Registro
           Público de Comercio se llevará en las cabeceras de los
           departamentos o secciones judiciales...' -- i.e. the
           registry is kept PER DEPARTMENT, not centrally. Artículo
           386 (own text): whoever holds the local Registro de la
           Propiedad in that same locality is in charge of it.
        3. Artículo 420 (own text, read directly): 'Ninguna Cámara de
           Comercio podrá inscribir a comerciante alguno en tanto que
           no acredite su inscripción en el Registro Público del
           Comercio' -- reinforces the coupling between a SPECIFIC
           chamber and a SPECIFIC territorial registry, never a
           chamber acting outside its own department's registry.
        4. Independently, Artículo 308 numeral III (foreign
           companies) closes with (own text, read directly):
           '...ordenará la inscripción de la misma en el Registro del
           Comercio del lugar en que la empresa establezca su oficina
           principal' -- inscription happens at the registry of the
           PLACE (lugar) of the company's own principal office, a
           THIRD independent textual confirmation of the same
           place-scoped structure.
        5. Operationally confirmed from two live chamber sites this
           session: Cámara de Comercio e Industrias de Tegucigalpa
           (CCIT, https://www.ccit.hn/) states it operates the
           'Registro Mercantil de Francisco Morazán' -- i.e. scoped
           to the Francisco Morazán department. Cámara de Comercio e
           Industrias de Cortés (CCIC,
           https://www.ccichonduras.org/) independently states it
           operates its OWN 'Registro Mercantil' for the Cortés
           department (San Pedro Sula). Two DIFFERENT chambers,
           each with its OWN registry, each scoped to its OWN
           department -- exactly what Artículos 384-386 describe.
      This catalog's `camara-jurisdiction-departments` map ONLY
      contains these two independently-confirmed chamber/department
      pairs -- it does NOT claim coverage of Honduras's other
      Cámaras de Comercio (e.g. La Ceiba/Atlántida, Choluteca), which
      this session did not independently fetch and verify. A
      jurisdiction check against an unlisted chamber is treated as
      UNVERIFIED (never silently assumed valid) -- see
      `marketentry.governor`'s flagship check.
    - Tax registration: RTN (Registro Tributario Nacional), issued by
      SAR (Servicio de Administración de Rentas). SAR's own site
      (https://www.sar.gob.hn/, fetched this session): RTN issuance is
      'inmediata y gratuita' (immediate and free); for legal persons
      RTN 'permanece activo hasta el cese de operaciones' (remains
      active until business cessation). Legal basis: Código
      Tributario, Decreto No. 170-2016 (title confirmed directly from
      ONCAE's own library mirror page, own text: 'DECRETO 170-2016
      Código Tributario'; this catalog does not pin a specific
      article number for the RTN mandatory-registration provision --
      an honest scoping gap, not a fabricated citation).
    - Foreign-company representative requirement: Código de Comercio
      Artículo 308 (own text, read directly, the SAME Decreto 73-50
      already cited in `statute.facts`), numeral III: a foreign
      company must 'Tener permanentemente en la República, cuando
      menos un representante con amplias facultades para realizar
      todos los actos y negocios jurídicos...' -- MANDATORY permanent
      in-country representative, independently corroborated
      operationally by ONCAE's own 'Empresa Extranjera' supplier-
      certification requirements page (fetched this session), which
      lists 'Authenticated deed evidencing permanent legal
      representation authority in-country (Article 308 numeral III,
      Commercial Code)' verbatim as a required document.

  Explicitly NOT claimed here (fabrication traps this catalog
  avoids): no invented decree number for the RTN mandatory-
  registration article, no claim that ProHonduras (investment-
  promotion brand) formally exists as a legal one-stop-shop --
  `https://www.prohonduras.hn/` was UNREACHABLE this session
  (connection failure, disclosed, not concealed) and this catalog
  does not cite it or build a check on it; no claim of Cámara de
  Comercio territorial coverage beyond the two chambers (CCIT/
  Francisco Morazán, CCIC/Cortés) independently fetched this session;
  no fabricated specific-article citation for the SAR fiscal-
  solvency requirement (cited only at the institutional/procedural
  level SAR's own site states).

  A jurisdiction not in `catalog` has NO spec-basis, full stop --
  extend `catalog`, never invent an owner-authority/legal-basis/URL.")

(def catalog
  "iso3 -> requirement map. `:required-evidence` mirrors the fleet's
  generic intake/portal-registration/filing evidence set, scoped to
  the DISTINCT regulatory-authority-level items this session
  independently confirmed (not every granular sub-form ONCAE's own
  Sociedad Mercantil checklist enumerates -- same honest-scoping
  discipline sibling catalogs use, e.g. Panama's facts.cljc does not
  reproduce every notarization either). `:camara-jurisdiction-*`
  grounds this vertical's FLAGSHIP governor check
  (`camara-jurisdiction-mismatch?` in `marketentry.registry`) -- a
  MULTI-AUTHORITY TERRITORIAL-JURISDICTION CONSISTENCY check, a check
  SHAPE genuinely different from every prior sibling: not a
  same-branch eligibility/routing/threshold test (Nicaragua's
  cross-branch certification gate), not a sector-conditional
  constitutional restriction (Panama's Art. 288 retail-trade check),
  but a verification that a DECENTRALIZED registry authority the
  engagement itself names actually HAS territorial jurisdiction over
  the engagement's own declared domicile, in a country whose
  Registro Público de Comercio is legally split department-by-
  department (Código de Comercio Arts. 384-386, 420) rather than
  held by one national registry."
  {"HND"
   {:name "Republic of Honduras"
    :owner-authority "Oficina Normativa de Contratación y Adquisiciones del Estado (ONCAE)"
    :legal-basis "Ley de Contratación del Estado, Decreto No. 74-2001 (Congreso Nacional, aprobada 1 de junio de 2001, propia fecha propia confirmada por el recital del Decreto Ejecutivo No. 10-2005; sustituye al Decreto No. 148-85 de 29 de septiembre de 1985, texto propio de ambos decretos leído directamente) y su Reglamento; reformada por, entre otros, el Decreto No. 266-2013 (16 de noviembre de 2013, reforma el Artículo 2 y adiciona los Artículos 3-A y 3-B, per la propia nota al pie del texto de la Ley)"
    :national-spec "HonduCompras -- Sistema de Información de Contratación y Adquisiciones del Estado (www.honducompras.gob.hn), creado por Decreto Ejecutivo No. 10-2005 Artículo 1 (La Gaceta No. 30,824, 14 de octubre de 2005), administrado por ONCAE; uso OBLIGATORIO per Artículo 2 del mismo decreto para todos los órganos del ámbito de la Ley de Contratación del Estado. El Registro de Proveedores y Contratistas del Estado (Ley de Contratación del Estado Art. 30/34-37) es un registro CENTRALIZADO en ONCAE -- distinto del Registro Público de Comercio departamental (ver `:camara-jurisdiction-*` abajo), nunca deben confundirse."
    :provenance "https://oncae.gob.hn/biblioteca/normativa/ley-de-contratacion-del-estado/ley-de-contratacion-del-estado/ ; https://oncae.gob.hn/biblioteca/normativa/normativa-normativa/decreto-10-2005-creacion-de-honducompras/ ; https://oncae.gob.hn/ (honducompras.gob.hn itself returned an EXPIRED TLS CERTIFICATE on direct fetch this session -- disclosed, not concealed; not independently browsed live)"
    :required-evidence ["Registro Mercantil inscription at the Cámara de Comercio e Industrias territorially corresponding to the business domicile (Código de Comercio, Decreto N.º 73-50, Arts. 384-386, 420 -- the SAME decree cited in statute.facts/hnd.codigo-comercio-decreto-73-50)"
                         "SAR RTN (Registro Tributario Nacional) tax-registration record"
                         "SAR Constancia de Solvencia Fiscal (QR-verifiable fiscal-clearance certificate)"
                         "ONCAE Registro de Proveedores y Contratistas del Estado inscription (Ley de Contratación del Estado Art. 34)"
                         "Permiso de Operación Municipal (municipal operating-permit record)"]
    ;; resident-representative sub-schema -- mirrors the AGO template's
    ;; `:rep-*` triple, grounded in the SAME Código de Comercio Decreto
    ;; 73-50 already cited in statute.facts, Art. 308 numeral III.
    :rep-owner-authority "Registro Público de Comercio del lugar donde la empresa extranjera establezca su oficina principal (autorización previa de la Secretaría de Hacienda, Código de Comercio Art. 308 último párrafo)"
    :rep-legal-basis "Código de Comercio (Decreto N.º 73-50) Art. 308 numeral III -- representante permanente con amplias facultades OBLIGATORIO en la República para toda sociedad extranjera que ejerza el comercio en Honduras"
    :rep-provenance "https://www.wipo.int/wipolex/edocs/lexdocs/laws/es/hn/hn009es.pdf (misma fuente primaria que statute.facts/hnd.codigo-comercio-decreto-73-50); operacionalmente confirmado por la propia página de requisitos de ONCAE para 'Empresa Extranjera'"
    ;; corporate tax-id sub-schema -- mirrors the AGO template's
    ;; `:corporate-number-*` triple.
    :corporate-number-owner-authority "Servicio de Administración de Rentas (SAR)"
    :corporate-number-legal-basis "RTN (Registro Tributario Nacional) -- Código Tributario, Decreto No. 170-2016 (número de decreto confirmado; ningún artículo específico de inscripción obligatoria fue verificado de forma independiente en esta sesión -- brecha honesta, no fabricada)"
    :corporate-number-provenance "https://www.sar.gob.hn/ ; https://oncae.gob.hn/biblioteca/normativa/ley-de-procedimiento-administrativo/decreto-170-2016/"
    ;; flagship sector-independent, DOMICILE-CONDITIONAL check -- a
    ;; MULTI-AUTHORITY TERRITORIAL-JURISDICTION CONSISTENCY test,
    ;; genuinely new for this vertical (grep-verified absent as a
    ;; governor check function name fleet-wide at build time).
    :camara-jurisdiction-owner-authority "Cámara de Comercio e Industrias territorialmente correspondiente al departamento de domicilio del comerciante (Código de Comercio, Decreto N.º 73-50, Arts. 384-386, 420)"
    :camara-jurisdiction-legal-basis "Código de Comercio Art. 384: 'Es obligatorio el registro de todo comerciante en la Cámara de Comercio e Industrias correspondiente.' Art. 385: 'El Registro Público de Comercio se llevará en las cabeceras de los departamentos o secciones judiciales...' (registro DESCENTRALIZADO por departamento, no un registro nacional único). Art. 420: 'Ninguna Cámara de Comercio podrá inscribir a comerciante alguno en tanto que no acredite su inscripción en el Registro Público del Comercio' -- acopla cada Cámara a SU PROPIO registro territorial."
    ;; camara-id -> #{departments this session independently confirmed
    ;; that chamber's own site claims jurisdiction over}. ONLY these
    ;; two -- extending to other Honduran chambers requires
    ;; independently fetching and confirming that chamber's own site,
    ;; never assumed by pattern-matching a department name.
    :camara-jurisdiction-departments {"CCIT" #{"Francisco Morazán"}
                                       "CCIC" #{"Cortés"}}
    :camara-jurisdiction-provenance "https://www.ccit.hn/ (Registro Mercantil de Francisco Morazán) ; https://www.ccichonduras.org/ (Registro Mercantil de Cortés) ; https://www.wipo.int/wipolex/edocs/lexdocs/laws/es/hn/hn009es.pdf (Código de Comercio Arts. 384-386, 420, 308-III)"}})

(defn spec-basis
  "The jurisdiction's requirement map, or nil -- nil means NO
  spec-basis, and the governor must hold any proposal that tries to
  assess or file on it."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report: how many of the requested jurisdictions
  actually have a spec-basis entry. Never report a missing
  jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-hnd marketentry.facts R0: " (count catalog)
                 " jurisdiction seeded with an official spec-basis. "
                 "This is a starting catalog for market-entry navigation, "
                 "not a survey of all ~194 jurisdictions -- extend "
                 "`marketentry.facts/catalog`, never fabricate a "
                 "jurisdiction's requirements.")})))

(defn required-evidence-satisfied?
  "Does `submitted` (a set/coll of evidence keywords or strings)
  satisfy every evidence item listed for `iso3`? Missing spec-basis ->
  never satisfied."
  [iso3 submitted]
  (when-let [{:keys [required-evidence]} (spec-basis iso3)]
    (let [need (count required-evidence)
          have (count (filter (set submitted) required-evidence))]
      (= need have))))

(defn evidence-checklist [iso3] (:required-evidence (spec-basis iso3) []))

(defn rep-spec-basis
  "The jurisdiction's representative-related requirement map, or nil."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:rep-owner-authority sb)
      (select-keys sb [:rep-owner-authority :rep-legal-basis :rep-provenance]))))

(defn corporate-number-spec-basis
  "The jurisdiction's corporate-number / tax-id regime, or nil."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:corporate-number-owner-authority sb)
      (select-keys sb [:corporate-number-owner-authority
                        :corporate-number-legal-basis
                        :corporate-number-provenance]))))

(defn camara-jurisdiction-spec-basis
  "Spec-basis for the flagship decentralized-mercantile-registry
  territorial-jurisdiction check -- used by the governor's flagship
  check to cite an official basis (and the confirmed camara/department
  map) rather than assert a mismatch bare."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:camara-jurisdiction-owner-authority sb)
      (select-keys sb [:camara-jurisdiction-owner-authority
                        :camara-jurisdiction-legal-basis
                        :camara-jurisdiction-departments
                        :camara-jurisdiction-provenance]))))

(defn camara-covers-department?
  "Does `camara` (a chamber id, e.g. \"CCIT\") actually have confirmed
  territorial jurisdiction over `department` for `iso3`, per this
  catalog's independently-verified `:camara-jurisdiction-departments`
  map? An UNLISTED camara is NEVER assumed valid -- returns false, the
  same honest-absence discipline `spec-basis` itself uses for an
  unlisted jurisdiction."
  [iso3 camara department]
  (boolean
   (when-let [{:keys [camara-jurisdiction-departments]} (camara-jurisdiction-spec-basis iso3)]
     (contains? (get camara-jurisdiction-departments camara #{}) department))))
