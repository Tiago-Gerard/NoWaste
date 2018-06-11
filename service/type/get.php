<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
require_once '../pdo.php';

$requestGetType == NULL;
echo getTypes();
function getTypes(){
    //mise en place d'un singleton pour gagner du temp sur les requette si elle ont deja été faites une fois
    if($requestGetType==NULL){
        $db = getDB();
        $requestGetType = $db->prepare("SELECT * FROM `Type`");
    }   
    $requestGetType->execute();
    
    $data = $requestGetType->fetchAll(PDO::FETCH_ASSOC);
    return json_encode($data);
}


