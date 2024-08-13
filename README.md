# Elastic Stack

## War in Ukraine/Food prices in Ukraine 

![Alt text](./elk/screenshot1.png "Part1")
![Alt text](./elk/screenshot2.png "Part2")


### Popis datasetu
Mám několik datasetů: ceny potravin na Ukrajině od roku 2014, záznamy teroristických raketových úderů na Ukrajinu, počet uprchlých běženců do Evropy, ruské ztráty na osobním a technickém složení. 

Datasety ruských ztrát a raketových úderů jsou založeny na zprávách ozbrojených sil Ukrajiny. Dataset s běženci je poskytnut data2.unhcr.org a je založen na otevřených datech Evropské unie. Dataset s cenami potravin je vzat z https://data.humdata.org. Tento dataset obsahuje data o cenách potravin na Ukrajině, pocházející z databáze cen potravin Světového potravinového programu. Databáze cen Světového potravinového programu pokrývá potraviny, jako je kukuřice, rýže, fazole, ryby a cukr pro 98 zemí a asi 3000 trhů.


### Zdroje
https://www.kaggle.com/datasets/piterfm/2022-ukraine-russian-war

https://www.kaggle.com/datasets/hskhawaja/russia-ukraine-conflict

https://data.humdata.org/dataset/wfp-food-prices-for-ukraine

https://data.humdata.org/dataset/ukraine-refugee-situation

https://data2.unhcr.org/en/situations/ukraine


### Formát dat
Všechny datasety byly záměrně vybrány ve formátu CSV. Všechny mají společný sloupec date, který umožňuje pohodlně provádět join. U všech dokumentů je použit oddělovač ",", což usnadnilo práci s parsováním dat.

### Provedené úpravy dat
Pro předzpracování dat byl použit Spark, který spouštěl skript v jazyce Scala. Dataset cen potravin obsahoval měsíční data od roku 2014 do roku 2023. Ostatní datasety, které se týkaly války na Ukrajině, měly data téměř za každý den od roku 2022. Abych mohl vytvořit dobrou vizualizaci a zachovat konzistenci dat, rozhodl jsem se spojit všechny CSV soubory kromě cen potravin a omezit data od 28. srpna 2022 do 29. listopadu 2023. Data cen potravin byla omezena od 15. března 2014 do 15. února 2022, což umožňuje sledovat, jaká ekonomická situace byla před masivní invazí. Výstupem skriptu byly dva CSV soubory (food_prices, merged_data), které automaticky přistály ve složce s Logstashem. V Logstash byly definovány dva pipeline, které odesílají data do Elasticsearch. Byly také definovány conf a template soubory. Conf soubory filtrují sloupce, které se dostanou do konečného indexu, automaticky vytvářejí pole "location" ze dvou polí "latitude" a "longitude" a nastavují správné datové typy pro sloupce integer a float. V json template souborech jsou popsána pravidla pro správné mapování dat.

## Závěr
Během semestrální práce jsem našel dobré datasety, které se týkají války na Ukrajině a cen potravin. Vytvořil jsem projekt, který může automaticky provádět předzpracování dat a odesílat tato data přímo do Elasticsearch pomocí Logstash. Podarilo se mi vytvořit 8 různých typů vizualizací, které pokrývají všechna data, která jsem měl.