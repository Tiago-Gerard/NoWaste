<?php
/*
	Projet:			NoWaste - Projet réalisé dans le cadre du TPI
	
	Page: 			create.php
	Description:            Crée une offre avec les données envoyer en post
	
	Auteur:			Tiago Gerard
*/
//La requette preparée sous forme d'un singleton

include '../functions.inc.php';
if(filter_has_var(INPUT_POST,"description")){
    $latitude = filter_input(INPUT_POST, 'latitude');
    $longitude = filter_input(INPUT_POST, 'longitude');
    //verifie si la position existe si non en crée une nouvelle
    $idPos = verifieSiLaPosexiste($latitude, $longitude);
    if($idPos==NULL){
        $idPos= createPos($latitude, $longitude);
    }
    $lienPhoto = mettreImageSurServeur(array('.png','.jpg','.jpeg'));
    $description = filter_input(INPUT_POST, 'description');
    $datePeremption = filter_input(INPUT_POST, 'datePeremption');
    $idUtilisateur = filter_input(INPUT_POST, 'idUtilisateur');
    $idType = filter_input(INPUT_POST, 'idType');
    create($lienPhoto,$description,$datePeremption,$idUtilisateur,$idType,$latitude,$longitude);           
}


