# Examen DSI 

Tenim un sistema que conté **usuaris** i les seves **notes** (o anotacions).

Dels usuaris guardem:
* username
* name
* secondName
* email

De les notes guardem:
* title
* content
* dateCreation
* dateEdit
* userName  *// és l'usuari propietari de la nota*
* checked   *// per saber segur que el propietari existeix (ja veurem més endavant l'utilitat)*

Aquest sistema ja el tenim implementat amb dos microserveis que funcionen i que segueixen (quasi) l'arquitectura hexagonal.
Com us podeu imaginar són dos microserveis molt senzill que només fan operacions CRUD amb els usuaris i notes sense tenir gens
de lògica de negoci.

El microservei d'usuaris NO agrega les notes d'aquests, és a dir, quan demanem una llista d'usuaris ens arriba només la 
informació d'aquests **sense** les seves notes. En aquest sentit són una mica diferents als que hem treballat a les pràctiques.

#### Arquitectura: (que ja teniu feta)

* Estan implementats seguint l’arquitectura hexagonal sense mapping. Al ser solament CRUD no cal complicar-nos la vida.
* Que siguin sense mapping vol dir que tots els components usen directament els objectes del domini
* Tenen els ports d’entrada i de sortida, i adaptadors web i de persistència
* Estan estructurats en paquets sense usar mòduls (per simplificar l’examen)

#### Comunicació: (que ja teniu feta)

* Ara mateix només hi ha implementada una comunicació via REST d’un cap a l’altre i està fet sense tenir en compte l’arquitectura hexagonal,
és a dir, sense usar ports i adaptadors.
* En aquesta comunicació ja implementada el microservei de *notes*, en el moment de creació d'una nota nova, comprova si l'userName realment 
existeix entre els usuaris. Si és així la nota es crea amb l'atribut *checked* a cert. Si l'usuari no existeix aleshores la nota no es crea.

#### Exemples de crides
**GET** per llistar usuaris i notes
* http://localhost:8080/users
* http://localhost:8080/users/josep
* http://localhost:8080/users/exists/josep (returna true o false depenent de si el usuari josep existeix)
* http://localhost:8081/notes
* http://localhost:8081/notes/josep

**POST** per crear usuaris i notes
* http://localhost:8080/users
  
On al body posarem l'usuari que volem crear
```
{
"username": "herminia",
"name": "Herminia",
"secondName": "Sanchez",
"email": "hsanchez@mail.cat"
}
```
* http://localhost:8081/notes

On al body posarem la nota que volem crear
```
{
    "title": "A REST note",
    "content": "This is the content of the note",
    "userName": "alvarez"
}
```

**DELETE** per esborrar usuaris
* http://localhost:8080/users/alvarez

On s'esborra l'usuari *alvarez*. **Atenció** perquè ara mateix quan s'esborra un usuari NO s'esborren les seves notes!!!

## Què heu de fer? 
Heu d'implementar els següents exercicis. Són obligatoris els TODO 1, TODO 2 i TODO 3. I heu d'escollir si voleu fer 
el TODO 4 o el TODO 5. Per tant si feu 4 exercicis podeu obtenir 10 punts. Si algú té temps pot implementar els dos darrers exercicis 
i obtenir un 12, una nota superior a 10, que pot anar bé de cara a fer la mitja!

* **TODO 1:** (Val 2 punts) Fer que la comunicació via REST esmentada anteriorment, i que ja està implementada, segueixi l’arquitectura hexagonal. És a dir que hi hagi el port i l'adaptador pertinents.
  Recorda que en aquesta comunicació el microservei de notes pregunta al microservei d'usuaris si l'usuari de la nota existeix.
* **TODO 2:** (Val 3 punts) Embolcallar la comunicació del punt anterior amb un *circuit breaker* de manera que quan el circuit estigui obert  
  la nota es crei igualment però amb l'atribut **checked** a false. D'aquesta forma aconseguim crear notes noves encara que el 
  microservei d'usuaris estigui caigut. En un procediment de tipus batch posterior es podria comprovar l'usuari de la nota i posar el checked a true 
  (però això és una història per un altre dia)
* **TODO 3:** (Val 3 punts) Volem que quan s'esborra un usuari també s'esborrin totes les seves notes. Per fer-ho el microservei d'usuaris enviarà 
  un missatge asíncron al de notes tot indicant el *userName* del que s'han d'esborrar les notes. Heu de tenir en conte que si hi ha més d'una 
  instància del microservei de notes només una rebi el missatge.
  Si voleu treure puntuació màxima en aquest exrcici heu de seguir l'arquitectura hexagonal, és a dir, implementar els ports i els adaptadors pertinents.
  El missatge pot ser in simple String amb el nom d'usuari o podeu crear una classe "contenidora" del missatge dins dels ports. Qualsevol
  de les dues opcions és bona i tindrà la mateixa puntuació.
* **TODO 4:** (Val 2 punts) Afegir un discovery service. Us heu d'assegurar que el restTemplate del primer i segon exercici esta balancejat (laodbalance) 
* **TODO 5:** (Val 2 punts) Afegir un edge (gateway) service que redireccioni les crides /users/* cap al microservei d'usuaris i les crides
/notes/* cap al microservei de notes. 
 
Dins del mateix codi hi ha comentaris del tipus TODO on hi podreu trobar alguna pista o aclaració

