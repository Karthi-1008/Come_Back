Nutrition Tracker App

Summary

Android open-source daily nutrition/health portfolio app (offline global food DB, Google sign-in, onboarding survey), built via Antigravity; v1 no AI, v2 AI chat bot

Details
Wants to build an app via Antigravity: fastest, best-looking UI/UX and design theme, very fast, offline-capable, minimal install size (higher memory use after install is fine)
Core idea: track everything a human needs in a day (protein, vitamins, minerals, other nutrients); search and tap any global food (dosa, burger, egg, keerai, leaves, fruits, vegetables, junk food, chocolate, cake, juices, water)
Log by meal time (morning, evening etc.) with quantity via easy slider/plus-minus in grams, ml, units or "one"
Daily portfolio shows what's covered and what's missing (e.g. minerals) and suggests available foods to fill gaps
Google sign-in, then onboarding survey (height, weight, body details, health goals like hair, hands, taste buds, eyesight, ears) to compute personal targets
Every like/dislike click updates the portfolio and suggestions continuously
Android only; open source on GitHub with community contributions
Design wanted: realistic 3D anatomy body, very engaging animations, must not feel like AI slop; one animation per nutrient (about 60 health factors, grouped to roughly 25 animation templates), shared by every food containing it; badges/streaks (e.g. Vitamin Hero, Calcium Hero, Grandmaster); will adjust design in Antigravity himself
Food records: vectors only, no photos even online; titles vary by local language; also gather common local/regional names (e.g. keerai/kira)
Version 1 languages in priority order: English, Tamil, Hindi, Malayalam, Telugu
Food search: each food has one English master title shown in results; search matches alternate names in Tamil, Hindi, Malayalam, Telugu scripts and English-letter spellings (e.g. keerai/kirai), no per-language food titles
Whole app interface language chosen in Settings: English default, then Tamil, Telugu, Malayalam, Hindi
Platform: Android 8 and above (including future versions), minimum 3 GB RAM, no maximum; storage size to be decided later; v1 fully offline
Body visualization: realistic 3D human, male or female per login profile, no nudity (parts of body only); animation shows real food journey (egg to stomach, stomach acid breaks it into particles, becomes fluid, enters blood vessels, travels to muscles/eyes/other parts and down to cell level); no excretion; fluid-like realistic effects, not sparkles/cracker lighting
No health-condition or healing visualization; purpose is only to show how food travels, splits and works, improving strength
Visual direction: no real 3D and no hired artist (pure vibe coding, free/open assets); 2.5D layered body (Rive/Lottie style, like UPI cashback reward animations) that feels 3D and realistic, not kids-cartoon; clothed figure with see-through window following the food; crystal-clear body parts and realistic liquid flows
Body must adapt to every user's age, height and weight (one base body morphing), stay small in APK and fast, and must not feel like a dummy model, should feel realistic for each body type
Food coverage priority: Indian homemade foods first, then Western and other countries' homemade, then global; then packaged, ready-made, instant, fast and junk foods. Wants a README mapping every vitamin/mineral/health factor to the foods and ingredients that supply it, as a project file for building the local food database (first separate document requested)
Daily targets use WHO values as the main reference. Onboarding survey is short: age, sex, height, weight, diet type (veg, non-veg, vegan) and one activity-level question with about four simple options; nothing else
Portfolio: all health factors tracked with equal weight on WHO daily values (and WHO daily limits with over-limit warnings for things like sodium, sugar, saturated fat); main screen shows only six category cards (vitamins, minerals, energy/macros, essential amino acids, essential fatty acids, bonus), not the full list; tapping a category shows its pending and fulfilled factors
Animations: every food logged plays its nutrient journey in the body; when about 80% of the 57 factors are complete, play a special full-body "master/pro user" animation; the score is live, so exceeding WHO limits (junk, sugar, salt) lowers it and can remove master status; junk stays junk, with only a simple calm balancing note, no warning alarms
Day boundaries follow the phone's own clock/time zone; app keeps last 30 days of history and, when opened the next morning, asks the user whether to keep or delete older data; history is viewed only inside the app portfolio; export is version 2, not v1
Streak is a daily streak: just opening the app counts as that day's check-in; missing a day only removes one streak point (gentle, pleasant, no reset to zero). Master badge is per day: earned at the constant 80% rule, expires next day, new badge next day
Badges are single-day only (no long-term or tiered badges like Calcium Hero/Grandmaster); only the daily streak is long-running
No food suggestions in version 1 (moved to version 2); v1 flow is search, add food, see visualization, view portfolio, exit. Light and dark mode both required; body visualization should be more attractive than the rest of the UI
Tech stack discussion: leaning Kotlin + Jetpack Compose, Room/SQLite offline DB, Rive for body animation, Google sign-in; wants Rust only in fast-critical places (search/nutrient engine) without adding complexity
Tech stack decision: version 1 uses Kotlin + Jetpack Compose only (no Rust), chosen for fastest speed, best compatibility, smallest size, best UI/UX and visualization; Rust to be added in version 2
Animation engine: Rive (best look, fast on small phones, tiny files). Whole app will be built entirely with Antigravity using Gemini high, so documents should be clear step-by-step instructions for a coding AI
Body artwork: no 3D artist or Blender; going with the free open-source BodyParts3D anatomy library (licence to be checked) flattened into the layered 2.5D look; build in about four stages in Antigravity, each tested before the next
Downloaded female organ GLB files from NIH 3D Human Reference Atlas: larynx, lung, main bronchus, pancreas, renal pelvis L/R, large intestine (SBU_F_Intestine_Large), heart, kidney L/R, liver, pelvis, small intestine, spleen; female stomach was not available there. Male parts to come from cloned BodyParts3D repo (olivercase/body_parts_3d_api)
Build workflow: one stage at a time, day by day, starting with a complete Stage 1 document only; wants documentation alone (no extra asset files, to save Claude credits); will handle the 3D body asset wiring himself
Plan: discuss first, then ask for separate documents per topic (design, database, features, v2); wants approximate app size finalised at the end
Version 1: local database only, fully offline, no AI. Version 2: simple AI chat bot that finds foods (e.g. "I had dosa today"), adds them to the log/portfolio automatically, recognises global foods; internet used for AI and refreshing only