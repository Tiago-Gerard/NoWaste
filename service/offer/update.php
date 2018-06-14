<?php
/*
	Projet:			NoWaste - Projet réalisé dans le cadre du TPI
	
	Page: 			update.php
	Description:            Modifie une offre avec les données envoyer en post
	
	Auteur:			Tiago Gerard
*/

include '../functions.inc.php';
if(filter_has_var(INPUT_POST,"idOffre")){
        $idPos=null;
        $latitude = filter_input(INPUT_POST,'latitude');
        $longitude = filter_input(INPUT_POST,'longitude');
        $idOffre = filter_input(INPUT_POST,'idOffre');
        if(verifieSiLaPosEstPareille($idOffre, $latitude, $longitude)==FALSE){            
            $idPos = verifieSiLaPosexiste($latitude, $longitude);
            $idPos = $idPos[0]['idPosition'];
            if($idPos==null){
            $idPos = createPos($latitude, $longitude);
            }
        }
        else{
            $idPos = getIdPos($idOffre);
        }       
        $description = filter_input(INPUT_POST,'description');
        $datePeremption = filter_input(INPUT_POST,'datePeremption');
        $idUtilisateur = filter_input(INPUT_POST,'idUtilisateur');
        $idType = filter_input(INPUT_POST,'idType');
        echo updateOffre($idOffre, $description, $datePeremption, $idType,$idPos);
        
    
}






