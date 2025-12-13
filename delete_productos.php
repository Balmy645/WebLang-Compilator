<?php
include 'db.php';
$id = $_GET['id'];
$stmt = $pdo->prepare("DELETE FROM productos WHERE id=?");
$stmt->execute([$id]);
echo "Eliminado";
?>