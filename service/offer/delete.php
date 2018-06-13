<?php

include '../functions.inc.php';
$idOffre = filter_input(INPUT_POST,'idOffre');

echo deleteOffre($idOffre);


