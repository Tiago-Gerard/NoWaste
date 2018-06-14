
<?php
/*
	Projet:			NoWaste - Projet réalisé dans le cadre du TPI
	
	Page: 			create.php
	Description:            Crée une offre avec les données envoyer en post
	
	Auteur:			Tiago Gerard
*/
include '../functions.inc.php';
$idOffre = filter_input(INPUT_POST,'idOffre');
echo deleteOffre($idOffre);


