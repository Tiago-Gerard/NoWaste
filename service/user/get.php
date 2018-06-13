<?php
/*
	Projet:			NoWaste - Projet réalisé dans le cadre du TPI
	
	Page: 			index.php
	Description:            Retourne l'Utilisateur en fonction des infomation envoyer en header
	
	Auteur:			Tiago Gerard
*/

// Require du PDO
include '../functions.inc.php';









///numero de téléphone
$user = $_SERVER['PHP_AUTH_USER'];
///numero + salage en sha1
$pass = $_SERVER['PHP_AUTH_PW'];

echo getUser($user,$pass);





