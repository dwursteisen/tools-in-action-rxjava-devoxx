# Tool's In Action RxJava

## Commencer avec RxJava

On vous conseils de regarder les slides de notre présentation [RxJava - Getting Started](https://speakerdeck.com/dwursteisen/rxjava-getting-started-1) sur speakerdeck

[![slide screensot](https://speakerd.s3.amazonaws.com/presentations/61d01b203d93013286d40a707911af0b/slide_31.jpg?1417995826)](https://speakerdeck.com/dwursteisen/rxjava-getting-started-1)

## Sandbox

Contient le code qui manipulera le webservice cf la classe ```Main```

## Server

Contient un serveur web qui est volontairement *lent*

Pour démarrer le server, il suffit de lancer le main de la class ```Server```

Il est possible de tester les webservices via [Swagger](http://localhost:8080/sdoc.jsp).

## Construction du projet

Il est possible d'executer le script ```build.sh``` (ou ```build.bat```) qui va executer la commande suivante :

    mvn install dependency:go-offline
    
Cette commande permet de builder le projet et surtout de récupèrer les dépendences pour pouvoir démarrer le tout sans connexion Internet.
