# FantasyTeams

Projet permettant calculer les meilleurs possiblités d'équipes pour le jeu NbaFantsay.

## Spécifications

Projet qui permet générer un fichier csv contenant les meilleurs équipes possibles (Equipe de 7 joueurs) pour le jeu NbaFantasy en fonction 
- des statistiques des joueurs founis en entrée (CSV)
- de la limite de prix indiqué

Le fichier CSV des stats des joueurs doit être de la forme :  
`{Players};{Equipe};{MJ};{Min};{Rb};{Pd};{Int};{Ct};{Bp};{Pts};{Pos};{Score};{Price};{Value};{Value W};{ValuePrice};{Matchs}`

Le fichier CSV généré en sortie est de la forme : 
`{player1};{player2};{player3};{player4};{player5};{player6};{player7};{priceTotal};{valueWeekTotal}`


## Compilation

Pour compiler le projet, lancer la commande suivante :  
`mvn clean install`

Le jar du projet est alors généré dans le répertoire target.

## Usage

Pour lancer le calcul des meilleurs équipes, lancer la commande suivante (Java 8 minimum) :  
`java -jar fantasyteam-1.2.0.jar` 

On peut définier les options soit dans le fichier de configuration `application.properties` ou directement en arguments de la ligne de commande (dans ce cas surcharge ceux du fichier de configuration).  
Arguments possibles en ligne de commande :
- `--pathCsvPlayers={}` : chemin absolu du fichier d'entrée des joueurs au format CSV  (exemple : "C:\Test\Players.csv")
- `--pathCsvResults={}` : chemin absolu du fichier de résultat au format CSV (exemple : "C:\Test\ResultTeams.csv")
- `--maxResultsCsv={}` : prix maximum autorisé pour le calcul des équipes
- `--teamLimitPrice={}` : nombre maximum de résultats à inclure dans le fichier CSV de résultat
- `--includePalyers={}` : optionnel, joueurs à inclure dans les équipes calculées (séparé par une virgule)
- `--request` : optionnel, pour interroger directement la bdd existante (sans calcul des équipes, nécessite donc au préalable d'un appel classique pour la création de la BDD)

Un fichier de log `fantasyTeams.log` est généré.

## Développement

### Debug 

Le projet a été effectué avec Spring Boot.  
Pour lancer l'application en debug (sous Eclipse), utiliser la commande `Debug As > Spring Boot App`. 
Penser au préalable à renseigner les arguments dans `Debug configurations` ou modifier directement dans le fichier `application.properties`.  
Exemple arguments à indiquer dans `Debug configurations` : 

```
--pathCsvPlayers=src/test/StatPlayersTest2.csv
--pathCsvResults=src/test/ResultComputeTeams.csv
--teamLimitPrice=105
--maxResultsCsv=250
--includePalyers="Luka Doncic,Nikola Vucevic"  
```

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/maven-plugin/reference/html/#build-image)
