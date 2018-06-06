<?php
/*
	Projet:			NoWaste - Projet réalisé dans le cadre du TPI
	
	Page: 			update.php
	Description:            Modifie une offre avec les données envoyer en post
	
	Auteur:			Tiago Gerard
*/

//include '../functions.inc.php';
if(filter_has_var(INPUT_POST,"idOffre")){
        $latitude = filter_input(INPUT_POST,'latitude');
        $longitude = filter_input(INPUT_POST,'longitude');
        $idOffre = filter_input(INPUT_POST,'idOffre');
        if(verifieSiLaPosEstPareille($idOffre, $latitude, $longitude)==FALSE){
            $idPos = createPos($latitude, $longitude);
        }
        else{
            $idPos = getIdPos($idOffre);
        }
        
        $lienPhoto = filter_input(INPUT_POST,'lienPhoto');
        
        if(verifieChangementImage($lienPhoto, $idOffre)==FALSE){
            $lienPhoto = mettreImageSurServeur(array('.png','.jpg','.jpeg'));
        }
        $description = filter_input(INPUT_POST,'description');
        $datePeremption = filter_input(INPUT_POST,'datePeremption');
        $idUtilisateur = filter_input(INPUT_POST,'idUtilisateur');
        $idType = filter_input(INPUT_POST,'idType');
        updateOffre($lienPhoto, $description, $datePeremption, $idUtilisateur, $idType,$idPos);
        
    
}






