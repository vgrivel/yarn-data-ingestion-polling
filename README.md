# yarn-data-ingestion-polling
Poll a datasource with Yarn and store data on the hadoop cluster. It is based on https://github.com/daplab/yarn-starter


# Getting Started

## Configuration
Open the conf/config.conf file and modify it with your settings.

## Building
```
mvn clean install
```

## Running
Run the script inside the project folder
```
 ./src/main/scripts/start-poller-ingestion-app.sh daplab-wn-21.fri.lan:2181 conf/config.conf
```
## On running operation


If you want to list all your yarn application, do it with the command:
```
yarn application -list
```

Logs of the application are available only when the application is closed.
First, kill the application.
```
yarn application -kill application_1426168278944_0001
```
Copy the distributed log on one file. It will simplify the error or message searching.
```
yarn logs -applicationId application_1430914769571_0001 >logs
```
# Data Explanation
CSV files that explains the columns and the Stations are located in the docs folder.

## Columns
Explanation of the different columns

| Column name | Unit | Description                                                                         |
|-------------|------|-------------------------------------------------------------------------------------|
| tre200s0    | °C   | Air temperature 2 m above ground; current value                                     |
| sre000z0    | min  | Sunshine duration; ten minutes total                                                |
| rre150z0    | mm   | Precipitation 1.5 m above ground; ten minutes total                                 |
| dkl010z0    | °    | Wind direction; ten minutes mean                                                    |
| fu3010z0    | km/h | Wind speed; ten minutes mean                                                        |
| pp0qnhs0    | hPa  | Pressure reduced to sea level according to standard atmosphere (QNH); current value |
| fu3010z1    | km/h | Gust peak (one second); maximum                                                     |
| ure200s0    | %    | Relative air humidity 2 m above ground; current value                               |
| prestas0    | hPa  | Pressure at station level (QFE); current value                                      |
| pp0qffs0    | hPa  | Pressure reduced to sea level (QFF); current value                                  |

## Stations
| stn | Name                     | Longitude/Latitude | KM-Coordinates | Altitude |
|-----|--------------------------|--------------------|----------------|----------|
| TAE | Aadorf / Tänikon         | 8°54'/47°29'       | 710514/259821  | 539      |
| COM | Acquarossa / Comprovasco | 8°56'/46°28'       | 714998/146440  | 575      |
| ABO | Adelboden                | 7°34'/46°30'       | 609400/148975  | 1320     |
| AIG | Aigle                    | 6°55'/46°20'       | 560400/130713  | 381      |
| ALT | Altdorf                  | 8°37'/46°53'       | 690174/193558  | 438      |
| AND | Andeer                   | 9°26'/46°37'       | 752687/164035  | 987      |
| BAS | Basel / Binningen        | 7°35'/47°32'       | 610911/265600  | 316      |
| BER | Bern / Zollikofen        | 7°28'/46°59'       | 601929/204409  | 552      |
| BEZ | Beznau                   | 8°14'/47°33'       | 659808/267693  | 325      |
| BIE | Bière                    | 6°21'/46°31'       | 515888/153206  | 683      |
| BUS | Buchs / Aarau            | 8°05'/47°23'       | 648389/248365  | 386      |
| FRE | Bullet / La Frétaz       | 6°35'/46°50'       | 534221/188081  | 1205     |
| CHA | Chasseral                | 7°03'/47°08'       | 570842/220154  | 1599     |
| CHU | Chur                     | 9°32'/46°52'       | 759471/193157  | 556      |
| CIM | Cimetta                  | 8°47'/46°12'       | 704433/117452  | 1661     |
| GSB | Col du Grand St-Bernard  | 7°10'/45°52'       | 579200/79720   | 2472     |
| DAV | Davos                    | 9°51'/46°49'       | 783514/187457  | 1594     |
| DIS | Disentis / Sedrun        | 8°51'/46°42'       | 708188/173789  | 1197     |
| ENG | Engelberg                | 8°25'/46°49'       | 674156/186097  | 1035     |
| EVO | Evolène / Villa          | 7°31'/46°07'       | 605415/106740  | 1825     |
| FAH | Fahy                     | 6°56'/47°25'       | 562458/252676  | 596      |
| GVE | Genève-Cointrin          | 6°08'/46°15'       | 498903/122624  | 420      |
| GLA | Glarus                   | 9°04'/47°02'       | 723752/210567  | 516      |
| GRH | Grimsel Hospiz           | 8°20'/46°34'       | 668583/158215  | 1980     |
| GOE | Gösgen                   | 7°58'/47°22'       | 640417/245937  | 380      |
| GUE | Gütsch ob Andermatt      | 8°37'/46°39'       | 690140/167590  | 2287     |
| GUT | Güttingen                | 9°17'/47°36'       | 738419/273960  | 440      |
| HOE | Hörnli                   | 8°56'/47°22'       | 713515/247755  | 1132     |
| INT | Interlaken               | 7°52'/46°40'       | 633019/169093  | 577      |
| JUN | Jungfraujoch             | 7°59'/46°33'       | 641930/155275  | 3580     |
| CDF | La Chaux-de-Fonds        | 6°48'/47°05'       | 550923/214893  | 1018     |
| DOL | La Dôle                  | 6°06'/46°25'       | 497061/142362  | 1669     |
| MLS | Le Moléson               | 7°01'/46°33'       | 567723/155072  | 1974     |
| LEI | Leibstadt                | 8°11'/47°36'       | 656378/272111  | 341      |
| OTL | Locarno / Monti          | 8°47'/46°10'       | 704160/114350  | 366      |
| LUG | Lugano                   | 8°58'/46°00'       | 717873/95884   | 273      |
| LUZ | Luzern                   | 8°18'/47°02'       | 665540/209848  | 454      |
| LAE | Lägern                   | 8°24'/47°29'       | 672250/259460  | 845      |
| MAG | Magadino / Cadenazzo     | 8°56'/46°10'       | 715475/113162  | 203      |
| MVE | Montana                  | 7°28'/46°18'       | 601706/127482  | 1427     |
| MRP | Monte Rosa-Plattje       | 7°49'/45°57'       | 629149/89520   | 2885     |
| MOE | Möhlin                   | 7°53'/47°34'       | 633050/269142  | 344      |
| MUB | Mühleberg                | 7°17'/46°58'       | 587788/202478  | 479      |
| NAP | Napf                     | 7°56'/47°00'       | 638132/206078  | 1403     |
| NEU | Neuchâtel                | 6°57'/47°00'       | 563150/205600  | 485      |
| CGI | Nyon / Changins          | 6°14'/46°24'       | 506880/139573  | 455      |
| PAY | Payerne                  | 6°57'/46°49'       | 562127/184612  | 490      |
| PIL | Pilatus                  | 8°15'/46°59'       | 661910/203410  | 2106     |
| PIO | Piotta                   | 8°41'/46°31'       | 695888/152261  | 990      |
| COV | Piz Corvatsch            | 9°49'/46°25'       | 783146/143519  | 3305     |
| PLF | Plaffeien                | 7°16'/46°45'       | 586808/177400  | 1042     |
| ROB | Poschiavo / Robbia       | 10°04'/46°21'      | 801850/136180  | 1078     |
| PUY | Pully                    | 6°40'/46°31'       | 540811/151514  | 455      |
| ROE | Robièi                   | 8°31'/46°27'       | 682587/144091  | 1894     |
| RUE | Rünenberg                | 7°53'/47°26'       | 633246/253845  | 611      |
| SBE | S. Bernardino            | 9°11'/46°28'       | 734112/147296  | 1638     |
| SAM | Samedan                  | 9°53'/46°32'       | 787210/155700  | 1708     |
| SHA | Schaffhausen             | 8°37'/47°41'       | 688698/282796  | 438      |
| SCU | Scuol                    | 10°17'/46°48'      | 817135/186393  | 1303     |
| SIO | Sion                     | 7°20'/46°13'       | 591630/118575  | 482      |
| STG | St. Gallen               | 9°24'/47°26'       | 747861/254586  | 775      |
| SBO | Stabio                   | 8°56'/45°51'       | 716034/77964   | 353      |
| SAE | Säntis                   | 9°21'/47°15'       | 744200/234920  | 2502     |
| ULR | Ulrichen                 | 8°18'/46°30'       | 666740/150760  | 1345     |
| VAD | Vaduz                    | 9°31'/47°08'       | 757718/221696  | 457      |
| VIS | Visp                     | 7°51'/46°18'       | 631149/128020  | 639      |
| WFJ | Weissfluhjoch            | 9°48'/46°50'       | 780615/189635  | 2690     |
| WYN | Wynau                    | 7°47'/47°15'       | 626400/233850  | 422      |
| WAE | Wädenswil                | 8°41'/47°13'       | 693849/230708  | 485      |
| ZER | Zermatt                  | 7°45'/46°02'       | 624350/97566   | 1638     |
| REH | Zürich / Affoltern       | 8°31'/47°26'       | 681428/253545  | 443      |
| SMA | Zürich / Fluntern        | 8°34'/47°23'       | 685117/248061  | 555      |
| KLO | Zürich / Kloten          | 8°32'/47°29'       | 682706/259337  | 426      |
| EBK | Ebnat-Kappel             | 9°07'/47°16'       | 726348/237167  | 623      |
| BIZ | Bischofszell             | 9°14'/47°30'       | 735325/262285  | 470      |
| SPF | Schüpfheim               | 8°01'/46°57'       | 643677/199706  | 742      |
| GIH | Giswil                   | 8°11'/46°51'       | 657320/188980  | 475      |
| EGO | Egolzwil                 | 8°00'/47°11'       | 642910/225537  | 521      |
| BUF | Buffalora                | 10°16'/46°39'      | 816494/170225  | 1968     |
| CRM | Cressier                 | 7°04'/47°03'       | 571160/210800  | 431      |
| NAS | Naluns / Schlivera       | 10°16'/46°49'      | 815374/188987  | 2400     |
| HAI | Salen-Reutenen           | 9°01'/47°39'       | 719102/279042  | 718      |
| GRE | Grenchen                 | 7°25'/47°11'       | 598216/225348  | 430      |
| CMA | Crap Masegn              | 9°11'/46°51'       | 732820/189380  | 2480     |
| ELM | Elm                      | 9°11'/46°55'       | 732265/198425  | 958      |
| GRA | Fribourg / Posieux       | 7°07'/46°46'       | 575182/180076  | 646      |
| LAG | Langnau i.E.             | 7°48'/46°56'       | 628005/198792  | 745      |
| HLL | Hallau                   | 8°28'/47°42'       | 677456/283472  | 419      |
| MER | Meiringen                | 8°10'/46°44'       | 655843/175920  | 588      |
| VAB | Valbella                 | 9°33'/46°45'       | 761637/180380  | 1569     |
| SCM | Schmerikon               | 8°56'/47°13'       | 713722/231496  | 408      |
| QUI | Quinten                  | 9°13'/47°08'       | 734848/221278  | 419      |
| PMA | Piz Martegnas            | 9°32'/46°35'       | 760267/160583  | 2670     |
| SMM | Sta. Maria, Val Müstair  | 10°26'/46°36'      | 828858/165569  | 1383     |
| KOP | Koppigen                 | 7°36'/47°07'       | 612662/218664  | 484      |
| ORO | Oron                     | 6°51'/46°34'       | 555502/158048  | 827      |
| PRE | St-Prex                  | 6°27'/46°29'       | 523549/148525  | 425      |
| RAG | Bad Ragaz                | 9°30'/47°01'       | 756907/209340  | 496      |
| CHD | Château-d'Oex            | 7°08'/46°29'       | 577041/147644  | 1029     |
| STK | Steckborn                | 8°59'/47°40'       | 715871/280916  | 398      |
| EIN | Einsiedeln               | 8°45'/47°08'       | 699981/221058  | 910      |
| EGH | Eggishorn                | 8°06'/46°26'       | 650279/141897  | 2893     |
| BOU | Bouveret                 | 6°51'/46°24'       | 555264/138175  | 374      |
| BRZ | Brienz                   | 8°04'/46°44'       | 647546/176806  | 567      |
| DEM | Delémont                 | 7°21'/47°21'       | 593269/244543  | 439      |
| MOA | Mosen                    | 8°14'/47°15'       | 660124/232846  | 452      |
| ATT | Les Attelas              | 7°16'/46°06'       | 586862/105305  | 2730     |
| GEN | Monte Generoso           | 9°01'/45°56'       | 722503/87456   | 1600     |
| GOR | Gornergrat               | 7°47'/45°59'       | 626900/92512   | 3129     |
| GRO | Grono                    | 9°10'/46°15'       | 733014/124080  | 323      |
| BOL | Boltigen                 | 7°23'/46°37'       | 595828/163588  | 820      |
| TIT | Titlis                   | 8°26'/46°46'       | 675400/180400  | 3040     |
| THU | Thun                     | 7°35'/46°45'       | 611202/177630  | 570      |

#File processing
By default, no processing is made to the data. They are directly written to the disk. If you want to process, just create a class that implements FileProcessing and fill the process method.
The processing method if useful for changing the file format (to CSV, JSON, ...) or removing headers, rows, null value, etc.


