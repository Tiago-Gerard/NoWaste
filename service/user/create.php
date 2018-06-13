<?php
/*
	Projet:			NoWaste - Projet réalisé dans le cadre du TPI
	
	Page: 			update.php
	Description:            Crée un nouvelle utilisateur avec les données envoyer en post
	
	Auteur:			Tiago Gerard
*/
include '../functions.inc.php';

if(filter_has_var(INPUT_POST,"numero")){
    
    $numero = filter_input(INPUT_POST, 'numero',FILTER_SANITIZE_STRING);
    $prenom = filter_input(INPUT_POST, 'prenom',FILTER_SANITIZE_STRING);
    $nom = filter_input(INPUT_POST, 'nom',FILTER_SANITIZE_STRING);
    $email = filter_input(INPUT_POST, 'email',FILTER_SANITIZE_EMAIL);
    createUser($nom,$prenom,$numero,$email);
    
}



