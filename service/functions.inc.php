<?php
/*
	Projet:			Eat@School - Projet réalisé dans le cadre du module 306 - Réaliser un petit projet informatique
	
	Page: 			index.php
	Description:            Retourne l'Utilisateur en fonction des infomation envoyer en header
 	
	Auteur:			Tiago Gerardd
*/

// Require du PDO
require "../pdo.php";

	


// Retourne toutes les écoles en Json
function login($numero,$pwd){
    $db = getDB();
    $request = $db->prepare("SELECT numero FROM `Utilisateur` WHERE numero=:numero");
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
function getOffer($id){
    
    $db = getDB();
    $request = $db->prepare("SELECT * FROM `Offre` WHERE idOffre=:idOffre");
    $request->bindParam(':idOffre',$id);
    $request->execute();
    $data = $request->fetchAll(PDO::FETCH_ASSOC);	
}





