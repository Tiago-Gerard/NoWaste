<?php
/*
	Projet:			Eat@School - Projet réalisé dans le cadre du module 306 - Réaliser un petit projet informatique
	
	Page: 			index.php
	Description:            Retourne l'Utilisateur en fonction des infomation envoyer en header
 	
	Auteur:			Tiago Gerardd
*/

// Require du PDO
require "../pdo.php";

//Les requettes preparées sous forme d'un singleton
static $requestLogin = NULL;
static $requestGetOffer=NULL;

// Identifie si la si les headers envoyer avec la requette HTTP sont correcte
function login($numero,$pwd){
    //mise en place d'un singleton pour gagner du temp sur les requette si elle ont deja été faites une fois
    if($requestLogin==NULL){
        $db = getDB();
        $request = $db->prepare("SELECT numero FROM `Utilisateur` WHERE numero=:numero");
    }
    
    $request->bindParam(':numero',$numero);
    $request->execute();
    
    $data = $request->fetchAll(PDO::FETCH_ASSOC);		
        if(sha1($data['numero'] . "lapinfuta45")==$pwd)
            {
            return true;
            }
        else{
            return json_encode("Access denied");
        }
    return json_encode($array);
}

//Retourne une offre par son identifiant
function getOffer($id){
    //mise en place d'un singleton pour gagner du temp sur les requette si elle ont deja été faites une fois
    if($requestGetOffer==NULL){
        $db = getDB();
        $request = $db->prepare("SELECT * FROM `Offre` WHERE idOffre=:idOffre");
    }    
    $request->bindParam(':idOffre',$id);
    $request->execute();
    $data = $request->fetchAll(PDO::FETCH_ASSOC);
    return $data;
}





