# AT
--- DEFAULTNI ZADATAK ---\\
• Korisnik unosi podatke pretrage ( cenu od-do i godiste od-do). Podatci se salju agentu sakupljacu putem JMS-a koji pronalazi 
automobile koji odgovaraju zadatim parametrima pretrage.\\
• Korisnik zatim ima mogucnost da unese nove parametre ( godiste, kilometrazu i snagu) koji se salju agentu koji ce da istrenira 
LinearRegression model i odradi predikciju cene na ovnovu datih parametara. Predikcija se salje masteru koji omogucava da se 
predikcija prikaze korisniku. Komunikacija do predictAgenta i do mastera ide preko JMS-a.

• U zadatku su implementirani PING i PONG agenti izmedju kojih je moguca razmena poruka. 

--- OSNOVNE KLIJENT SERVER FUNKCIONALNOSTI ---

• GET /agents/classes – dobavi listu svih tipova agenata na sistemu;
• GET /agents/running – dobavi sve pokrenute agente sa sistema;
• PUT /agents/running/{type}/{name} – pokreni agenta određenog tipa sa zadatim imenom;
• DELETE /agents/running/{aid} – zaustavi određenog agenta;
• POST /messages – pošalji ACL poruku;
• GET /messages – dobavi listu performativa.

--- KLIJENT SERVER DODATNE FUNKCIONALNOSTI ---

• GET /search/{year_from}/{year_to}/{priceFrom}/{priceTo} – dobavi podatke sa sajta polovniautomobili.com
• GET /predict/{year}/{km}/{power} – izvrsi predikciju cene na osnovu prosledjenih parametara

--- OSNOVNE SERVER SERVER FUNKCIONALNOSTI ---

• POST /node – Nov ne-master čvor kontaktira master čvor koji ga registruje;
• GET /agents/classes – Master čvor traži spisak tipova agenata koje podržava nov ne-master čvor;
• POST /node – Master čvor javlja ostalim ne-master čvorovima da je nov ne-master čvor ušao
• POST /agents/classes – Master čvor dostavlja spisak novih tipova agenata ostalim ne- master čvorovima
• POST /nodes – Master čvor dostavlja spisak ostalih ne-master čvorova novom ne-master čvoru
• POST /agents/classes – Master čvor dostavlja spisak tipova agenata novom ne-master čvoru
• POST /agents/running – Master čvor dostavlja spisak pokrenutih agenata novom ne-master čvoru
• GET /node – Heartbeat protokol
• DELETE /node/{alias} – Master čvor javlja ostalim ne-master čvorovima da obrišu čvor koji nije uspeo da odgovori na prethodni 
zahtev dva puta
