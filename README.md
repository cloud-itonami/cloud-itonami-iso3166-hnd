# cloud-itonami-iso3166-hnd

Open ISO 3166 Blueprint for **HND**: Republic of Honduras -- **`:implemented`**.

This repository designs **and implements** a forkable OSS business for
an independent public-sector market-entry consultant: an already-
incorporated operator (e.g. a `cloud-itonami-cofog-{code}`,
`cloud-itonami-isco-{code}`, `cloud-itonami-unspsc-{segment}` or
`cloud-itonami-{ISIC}` blueprint fork) gets a **MarketEntry-LLM**
Compliance Advisor + independent **Market-Entry Compliance Governor**
to navigate public-procurement registration (HonduCompras / ONCAE),
local business/tax registration (Cámara de Comercio Registro
Mercantil, SAR RTN), and local-content rules in Honduras, so the
operator can win and service a government contract without hiring a
full in-house compliance department.

## Checks

Five checks, in priority order, evaluated by `marketentry.governor` on
every `MarketEntry-LLM` proposal. The first four are HARD violations a
human approver cannot override; double-actuation guards are counted
separately. The confidence/actuation gate (item 5) is SOFT -- but see
Actuation below, `:filing/draft`/`:filing/submit` never auto-commit
regardless.

| # | Check | Grounds | Source |
|---|---|---|---|
| 1 | **Spec-basis** -- a `:jurisdiction/assess`/`:filing/draft`/`:filing/submit` proposal must cite an official source, never an invented one | `marketentry.facts/spec-basis` | oncae.gob.hn, honducompras.gob.hn |
| 2 | **Evidence incomplete** -- for draft/submit, the jurisdiction's full required-evidence checklist must be on file: (a) territorial Cámara de Comercio Registro Mercantil inscription, (b) SAR RTN tax registration, (c) SAR Constancia de Solvencia Fiscal, (d) ONCAE Registro de Proveedores y Contratistas inscription, (e) municipal operating permit | `marketentry.facts/required-evidence-satisfied?` | Código de Comercio (Decreto N.º 73-50) Arts. 384-386, 420; Código Tributario Decreto No. 170-2016; Ley de Contratación del Estado Decreto No. 74-2001 Art. 34 |
| 3 | **Cámara jurisdiction mismatch** (flagship, domicile-conditional) -- for submit, INDEPENDENTLY verify whether the engagement's own claimed `:registering-camara` actually has CONFIRMED territorial jurisdiction over its own claimed `:business-domicile-department`; fires ONLY on a genuine mismatch, NEVER for a confirmed-matching chamber/department pair -- not a blanket rule against any chamber | `marketentry.governor/camara-jurisdiction-mismatch-violations` | Código de Comercio (Decreto N.º 73-50) Arts. 384-386, 420 -- Honduras's Registro Público de Comercio is split department-by-department, independently confirmed against CCIT (Registro Mercantil de Francisco Morazán) and CCIC (Registro Mercantil de Cortés) |
| 4 | **Engagement fee mismatch** -- for submit, independently recompute `claimed-fee = base-fee + monthly-rate x monitoring-months` | `marketentry.registry/engagement-fee-matches-claim?` | ground-truth recompute (fleet-standard discipline) |
| 5 | **Confidence floor / actuation gate** (SOFT) -- LLM confidence below 0.6, or the op is `:filing/draft`/`:filing/submit` -> escalate to human | `marketentry.governor/check` | this vertical's own Trust Controls (`docs/business-model.md`) |

Two further double-actuation guards (`already-drafted`,
`already-submitted`) refuse to draft or submit the SAME engagement
twice, enforced off dedicated `:drafted?`/`:submitted?` booleans, never
a `:status` value.

Check 3 is deliberately **domicile-conditional, not a blanket rule**:
`test/marketentry/governor_contract_test.clj`'s
`camara-jurisdiction-mismatch-does-not-fire-for-a-correct-match`
proves a San Pedro Sula (Cortés) company correctly claiming CCIC
proceeds through the ordinary escalate-then-approve path with no HARD
hold, while `camara-jurisdiction-mismatch-is-held-and-unoverridable`
proves the SAME Cortés domicile claimed under CCIT (the Francisco
Morazán chamber) instead is an un-overridable HARD hold. Two
independent, contrasting fixtures (`eng-4`, `eng-5`) in
`marketentry.store/demo-data` exercise both branches.

**Honduras's mercantile registry is territorially decentralized, a
structural fact this repository confirmed from the primary law text
itself, not merely assumed:** Código de Comercio (Decreto N.º 73-50 --
the SAME decree already cited in this repo's own
`src/statute/facts.cljc`) Art. 384 requires registration at "la Cámara
de Comercio e Industrias correspondiente"; Art. 385 states the
Registro Público de Comercio "se llevará en las cabeceras de los
departamentos" (kept PER DEPARTMENT, never one national registry);
Art. 420 forbids any chamber from registering a merchant who cannot
prove inscription in that SAME territorial registry. This was
independently corroborated operationally against two live chamber
websites this session: Cámara de Comercio e Industrias de Tegucigalpa
(https://www.ccit.hn/, "Registro Mercantil de Francisco Morazán") and
Cámara de Comercio e Industrias de Cortés
(https://www.ccichonduras.org/, its own "Registro Mercantil" for
Cortés/San Pedro Sula). `marketentry.facts/camara-covers-department?`
exposes ONLY these two independently-confirmed chamber/department
pairs -- an unlisted chamber is NEVER assumed to have jurisdiction
over any department; see `test/marketentry/facts_test.clj`'s
`camara-covers-department-is-honestly-scoped`.

**ONCAE's own Registro de Proveedores y Contratistas del Estado is,
by contrast, explicitly CENTRALIZED** (Ley de Contratación del Estado,
Decreto No. 74-2001, Art. 34: "La Oficina Normativa... llevará un
registro centralizado"). This repo's `marketentry.facts` deliberately
states this contrast so no proposal conflates ONCAE's one national
supplier registry with the Cámara de Comercio's department-scoped
mercantile registry -- two DIFFERENT registries with two DIFFERENT
jurisdictional shapes.

## Actuation

**Drafting a real HonduCompras filing / portal registration and
submitting a real filing are never autonomous, at any phase, by
construction.** Two independent layers enforce this:

- `marketentry.governor`'s `high-stakes` set
  (`#{:actuation/draft-filing :actuation/submit-filing}`) always
  escalates, regardless of confidence.
- `marketentry.phase`'s phase table (`phase 0` through `phase 3`)
  never puts `:filing/draft` or `:filing/submit` in any phase's
  `:auto` set -- see `marketentry.phase`'s own docstring and
  `test/marketentry/phase_test.clj`'s `filing-submit-never-auto`, plus
  `test/marketentry/governor_contract_test.clj`'s
  `filing-draft-and-submit-never-auto-commit`.

The actor may intake an engagement, assess a jurisdiction and draft a
recommendation; a human market-entry operator is always the one who
actually files a draft or a submission. Grounded directly in this
blueprint's own [`docs/business-model.md`](docs/business-model.md) and
`marketentry.governor`'s own namespace docstring, which names this
vertical's Trust Controls verbatim: "any actual portal registration or
filing submission requires Market-Entry Compliance Governor clearance
and always escalates to human sign-off"; "a false or fabricated
regulatory-requirement claim is a HARD hold". `:filing/draft` and
`:filing/submit` apply SEQUENTIALLY to the SAME engagement record
(draft first, submit later) -- matching every sibling
`market-entry-compliance-governor` actor's own sequential shape.

## No robotics premise — digital/data service exemption

Market-entry and procurement-compliance navigation is a pure data/software
service with no physical-domain work (portal registration, document
checklists, regulatory-change monitoring) — the same exemption class as
`cloud-itonami-6310` (HR SaaS replacement) and `cloud-itonami-gtin-*`.
`blueprint.edn` sets `:itonami.blueprint/robotics false` and
`:required-technologies` lists only real capabilities (`:identity`,
`:forms`, `:dmn`, `:bpmn`, `:audit-ledger`), no `:robotics`.

## Core Contract

```text
operator intake + prior filing history
        |
        v
Compliance Advisor -> Market-Entry Compliance Governor -> filing draft, or human sign-off
        |
        v
gated portal registration / filing submission + audit ledger
```

No automated proposal can submit a portal registration or filing the
governor refuses, suppress a compliance record, or claim a legal/tax
conclusion the governor has not cleared. `:filing/submit` is never in any
phase's `:auto` set — it always requires human sign-off (mirrors
`cloud-itonami-M6910`'s `filing-submit-never-auto-at-any-phase`
invariant).

## What this is NOT

- **Not the government of Honduras.** See
  [`docs/business-model.md`](docs/business-model.md) for the boundary with
  `com-etzhayyim-ooyake` (read-only civic mirror), `matsurigoto` (sovereign
  statecraft), `com-etzhayyim-toritsugi` (individual citizen concierge),
  `legal-entity.etzhayyim.com` (read-only data aggregation), and
  `cloud-itonami-M6910` (company incorporation — a different regulatory
  phase this blueprint assumes is already complete).
- **Not legal or tax advice.** Every regulatory claim must cite the
  official source and route final filings to Honduran-licensed counsel
  or a registered agent where the law requires licensed representation.

## Capability layer

Resolves via [`kotoba-lang/iso3166`](https://github.com/kotoba-lang/iso3166)
(ISO 3166 `HND`). Required capabilities:

- :identity
- :forms
- :dmn
- :bpmn
- :audit-ledger

See [`docs/business-model.md`](docs/business-model.md) and
[`docs/operator-guide.md`](docs/operator-guide.md).

## Run

```bash
clojure -M:dev:run     # walk a clean intake -> assess -> draft -> submit lifecycle, plus HARD-hold scenarios (camara jurisdiction mismatch included)
clojure -M:dev:test    # governor contract · phase invariants · store parity · registry conformance · facts coverage
clojure -M:lint        # clj-kondo (errors fail; CI mirrors this)
```

## License

AGPL-3.0-or-later.

## Market-entry / statute catalogs

Governed public-sector market-entry compliance actor, same architecture
as the other `cloud-itonami-iso3166-*` siblings:

- `src/marketentry/{facts,governor,phase,sim,operation,registry,store,
  marketentryllm}.cljc` -- the actor. `facts.cljc` cites the Ley de
  Contratación del Estado (Decreto No. 74-2001, own text confirms it
  supersedes Decreto No. 148-85 de 1985), HonduCompras (Decreto
  Ejecutivo No. 10-2005, own text read directly), ONCAE's own
  Registro de Proveedores y Contratistas del Estado (the SAME Decreto
  74-2001 Art. 34, explicitly CENTRALIZED), SAR RTN (Código
  Tributario, Decreto No. 170-2016), and — reusing the SAME Código de
  Comercio (Decreto N.º 73-50) citation already established in this
  repo's own `src/statute/facts.cljc`, never a second, different one —
  the flagship territorially-decentralized Cámara de Comercio /
  Registro Público de Comercio regime (Arts. 384-386, 420) and the
  foreign-company permanent-representative requirement (Art. 308
  numeral III). `governor.cljc`'s flagship check independently
  verifies whether an engagement's claimed registering chamber
  actually covers its claimed domicile department -- a check SHAPE
  genuinely different from siblings whose flagship check is a
  sector-conditional constitutional restriction (Panama) or a
  cross-branch certification-threshold gate (Nicaragua): this one is a
  MULTI-AUTHORITY TERRITORIAL-JURISDICTION CONSISTENCY test, grounded
  in a country whose commercial registry has no single national
  instance (see the namespace docstrings and
  `test/marketentry/governor_contract_test.clj`'s two contrasting
  fixtures for the full honest disclosure).
- `src/statute/facts.cljc` -- general-law catalog (pre-existing, not
  modified by this Wave): Código de Comercio (Decreto N.º 73-50) and
  the Ley de Transparencia y Acceso a la Información Pública (Decreto
  Legislativo N.º 170-2006).

Every citation is fetch-verified against an official source this
session (oncae.gob.hn including its own PDF-hosted primary law texts,
sar.gob.hn, ccit.hn, ccichonduras.org, wipo.int/wipolex) or extracted
directly from the primary decree PDFs via `pdftotext`; sources that
were unreachable this session (honducompras.gob.hn — expired TLS
certificate on direct fetch; prohonduras.hn — connection failure) are
named explicitly rather than guessed at, and are NOT cited as
independently-browsed live sources — see `marketentry.facts`'s own
docstring for the full honest disclosure of which citation is a
live-verified URL vs. a primary-PDF-only confirmation vs. a disclosed
reachability gap.

## Culture catalog

Alongside the market-entry / statute catalogs, this repo carries a
**country-level regional-culture catalog** (ADR-2607171400 addendum 2,
`cloud-itonami-municipality-culture-catalog` Wave 1, in
`com-junkawasaki/root`) — national dishes, protected products, beverages,
crafts, festivals and heritage sites for Honduras:

- `src/culture/facts.cljc` — the catalog, source of truth (keyed by
  uppercase ISO3, mirroring `statute.facts`).
- `schema/culture.edn` — DataScript schema.
- `data/culture-tx.edn` — derived DataScript tx-data (regenerated from
  the catalog, never hand-edited).

City-level counterparts live in the `cloud-itonami-municipality-*` repos.
Same provenance discipline as the compliance catalogs: every entry cites a
source URL that was actually fetched and read on `:culture/retrieved-at`;
summaries state only what the cited source confirms. An item not in
`culture.facts/catalog` has no spec-basis — never fabricate one.
