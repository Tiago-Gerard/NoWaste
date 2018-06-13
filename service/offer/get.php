<?php
/*
	Projet:			NoWaste - Projet réalisé dans le cadre du TPI
	
	Page: 			index.php
	Description:            Retourne l'Utilisateur en fonction des infomation envoyer en header
	
	Auteur:			Tiago Gerard
*/

// Require du PDO
include '../functions.inc.php';
//static $requestGet =NULL;



if(filter_has_var(INPUT_SERVER,'PHP_AUTH_USER')){
    ///numero de téléphone
    $user = $_SERVER['PHP_AUTH_USER'];
    ///numero + salage en sha1
    $pass = $_SERVER['PHP_AUTH_PW'];
    //afunction getMesOffre($user);

}
if(filter_has_var(INPUT_GET,"idUtilisateur")&&(filter_has_var(INPUT_GET,"latitude")==false)){
    $idUtilisateur = filter_input(INPUT_GET,'idUtilisateur');
    echo geMytOffer($idUtilisateur);
}
if(filter_has_var(INPUT_GET,"latitude")&&(filter_has_var(INPUT_GET,"idType")==false)){
    $latitude = filter_input(INPUT_GET, 'latitude');
    $longitude = filter_input(INPUT_GET, 'longitude');
    $idUtilisateur = filter_input(INPUT_GET,'idUtilisateur');
    
    echo getOffreProche($latitude,$longitude,$idUtilisateur);
    
}
if(filter_has_var(INPUT_GET,"idType")){
    $latitude = filter_input(INPUT_GET, 'latitude');
    $longitude = filter_input(INPUT_GET, 'longitude');
    $idUtilisateur = filter_input(INPUT_GET,'idUtilisateur');
    $idType = filter_input(INPUT_GET,'idType');
    
    echo getOffreProcheByType($latitude,$longitude,$idUtilisateur,$idType);
    
}

//Retourne une offre par son identifiant







