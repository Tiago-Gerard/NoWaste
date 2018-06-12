<?php

require '../pdo.php';
$idOffre = filter_input(INPUT_POST,'idOffre');
static $requestDelete=NULL;
echo deleteOffre($idOffre);

function deleteOffre($idOffre){
    if($requestDelete == NULL){
        $db = getDB();
    $requestDelete = $db->prepare("DELETE FROM `Offre` WHERE Offre.idOffre=:idOffre");
    }
    
    $requestDelete->bindParam(':idOffre',$idOffre);
    $requestDelete->execute();
    return json_encode(true);
}
