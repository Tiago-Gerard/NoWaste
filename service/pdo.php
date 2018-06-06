<?php
/*
	Projet:			Eat@School - Projet réalisé dans le cadre du module 306 - Réaliser un petit projet informatique
	
	Page: 			pdo.php
	Description: 	Contient les informations de connexion à la base de données
	
	Auteur:			Tiago Gerard 
					
*/
// Constantes
DEFINE('DBHOST',"localhost");
DEFINE('DBUSER',"serviceClient");
DEFINE('DBPASS',"service4264");
DEFINE('DBNAME', "nowaste");
static $pdo = null;
header("Content-Type: application/json; charset=UTF-8");

// Connexion à la base de données
function getDB(){
    if(is_null($pdo)){

    $dsn = 'mysql:dbname='.DBNAME.';host='.DBHOST;
    try{
    $pdo = new PDO($dsn,DBUSER,DBPASS);
    } catch (PDOException $e){
        echo 'Connexion �chou�e : '.$e->getMessage();
    }}
    return $pdo;
}

