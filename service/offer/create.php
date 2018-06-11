<?php
/*
	Projet:			NoWaste - Projet réalisé dans le cadre du TPI
	
	Page: 			create.php
	Description:            Crée une offre avec les données envoyer en post
	
	Auteur:			Tiago Gerard
*/
//La requette preparée sous forme d'un singleton

include '../functions.inc.php';
if(filter_has_var(INPUT_GET,"description")){
    $latitude = filter_input(INPUT_GET, 'latitude');
    $longitude = filter_input(INPUT_GET, 'longitude');
    //verifie si la position existe si non en crée une nouvelle
    $idPos = verifieSiLaPosexiste($latitude, $longitude);
    if($idPos==NULL){
        $idPos= createPos($latitude, $longitude);
    }
    else{
        $idPos = $idPos[0]['idPosition'];
    }
    $lienPhoto = mettreImageSurServeur(array('.png','.jpg','.jpeg'),$_FILES['image']);
    $description = filter_input(INPUT_GET, 'description');
    $datePeremption = filter_input(INPUT_GET, 'datePeremption');
    $idUtilisateur = filter_input(INPUT_GET, 'idUtilisateur');
    $idType = filter_input(INPUT_GET, 'idType');
    //var_dump(createOffre($lienPhoto,$description,$datePeremption,$idUtilisateur,$idType,$idPos));
    createOffre($lienPhoto,$description,$datePeremption,$idUtilisateur,$idType,$idPos);     
    
}


