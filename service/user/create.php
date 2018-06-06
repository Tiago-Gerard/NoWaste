<?php
/*
	Projet:			NoWaste - Projet réalisé dans le cadre du TPI
	
	Page: 			update.php
	Description:            Crée un nouvelle utilisateur avec les données envoyer en post
	
	Auteur:			Tiago Gerard
*/
//La requette preparée sous forme d'un singleton
require '../pdo.php';
static $requestCreate=NULL;
if(filter_has_var(INPUT_POST,"numero")){
    
    $numero = filter_input(INPUT_POST, 'numero',FILTER_SANITIZE_STRING);
    $prenom = filter_input(INPUT_POST, 'prenom',FILTER_SANITIZE_STRING);
    $nom = filter_input(INPUT_POST, 'nom',FILTER_SANITIZE_STRING);
    $email = filter_input(INPUT_POST, 'email',FILTER_SANITIZE_EMAIL);
    create($nom,$prenom,$numero,$email);
    
}

function create($nom,$prenom,$numero,$email){
    //mise en place d'un singleton pour gagner du temp sur les requette si elle ont deja été faites une fois
    if($requestCreate==NULL){
       $db = getDB();
       $requestCreate = $db->prepare("INSERT INTO `Utilisateur`(`nom`, `prenom`, `numero`, `email`) VALUES (:nom,:prenom,:numero,:email)");     
    }
    $requestCreate->bindParam(':email',$email,PDO::PARAM_STR);
    $requestCreate->bindParam(':nom',$nom,PDO::PARAM_STR);
    $requestCreate->bindParam(':prenom',$prenom,PDO::PARAM_STR);
    $requestCreate->bindParam(':numero',$numero,PDO::PARAM_STR);
    $requestCreate->execute();
    
    
    
}

