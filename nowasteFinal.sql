-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Jeu 14 Juin 2018 à 12:57
-- Version du serveur :  10.0.33-MariaDB-0ubuntu0.16.04.1
-- Version de PHP :  7.0.25-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `nowaste`
--

-- --------------------------------------------------------

--
-- Structure de la table `Offre`
--

CREATE TABLE `Offre` (
  `idOffre` int(4) NOT NULL COMMENT 'identifiant de l''offre',
  `lienPhoto` varchar(300) NOT NULL,
  `description` text NOT NULL,
  `datePeremption` varchar(30) NOT NULL,
  `idUtilisateur` int(11) NOT NULL,
  `idType` int(11) NOT NULL,
  `idPosition` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `Offre`
--

INSERT INTO `Offre` (`idOffre`, `lienPhoto`, `description`, `datePeremption`, `idUtilisateur`, `idType`, `idPosition`) VALUES
(109, '0.19115600-152897824520180614-140843.jpg', 'brochette de fruits', '2018 06 15', 8, 3, 80),
(110, '0.91259600-152897844520180614-141333.jpg', 'paquet de 4 glace au daim', '2018 06 21', 30, 7, 80),
(111, '0.49899500-152897974520180614-142312.jpg', '4 croissants au chocolat', '2018 06 16', 2, 7, 82);

-- --------------------------------------------------------

--
-- Structure de la table `Position`
--

CREATE TABLE `Position` (
  `idPosition` int(11) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `Position`
--

INSERT INTO `Position` (`idPosition`, `latitude`, `longitude`) VALUES
(79, 46.1957708, 6.110415),
(80, 46.195740099999995, 6.110350899999999),
(81, 46.1799379, 5.9170443),
(82, 46.1743003, 6.1412226);

-- --------------------------------------------------------

--
-- Structure de la table `Type`
--

CREATE TABLE `Type` (
  `idType` int(1) NOT NULL,
  `nom` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `Type`
--

INSERT INTO `Type` (`idType`, `nom`) VALUES
(3, 'Fruits'),
(4, 'Légume'),
(5, 'Viande'),
(6, 'Poisson'),
(7, 'Autre');

-- --------------------------------------------------------

--
-- Structure de la table `Utilisateur`
--

CREATE TABLE `Utilisateur` (
  `idUtilisateur` int(2) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `numero` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `Utilisateur`
--

INSERT INTO `Utilisateur` (`idUtilisateur`, `nom`, `prenom`, `numero`, `email`) VALUES
(1, 'hasack', 'paulas', '+41783421233', 'paul.ack@gmail.com'),
(2, 'Buonomo', 'Ottavio', '+41798916581', 'ottavio.bnm@eduge.ch'),
(8, 'ack', 'paul', '+41783421232', 'paul.ack@gmail.com'),
(9, 'Herrmann', 'Constantin', '+41798828925', 'cocoh2012@gmail.com'),
(28, 'emulateur', 'emulateur', '+15555215554', 'emulateur@google.ch'),
(30, 'gerard', 'tiaao', '+41791931253', 'tiago');

--
-- Index pour les tables exportées
--

--
-- Index pour la table `Offre`
--
ALTER TABLE `Offre`
  ADD PRIMARY KEY (`idOffre`),
  ADD KEY `idUtilisateur` (`idUtilisateur`,`idType`,`idPosition`),
  ADD KEY `idType` (`idType`),
  ADD KEY `idPosition` (`idPosition`);

--
-- Index pour la table `Position`
--
ALTER TABLE `Position`
  ADD PRIMARY KEY (`idPosition`);

--
-- Index pour la table `Type`
--
ALTER TABLE `Type`
  ADD PRIMARY KEY (`idType`);

--
-- Index pour la table `Utilisateur`
--
ALTER TABLE `Utilisateur`
  ADD PRIMARY KEY (`idUtilisateur`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `Offre`
--
ALTER TABLE `Offre`
  MODIFY `idOffre` int(4) NOT NULL AUTO_INCREMENT COMMENT 'identifiant de l''offre', AUTO_INCREMENT=112;
--
-- AUTO_INCREMENT pour la table `Position`
--
ALTER TABLE `Position`
  MODIFY `idPosition` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=83;
--
-- AUTO_INCREMENT pour la table `Type`
--
ALTER TABLE `Type`
  MODIFY `idType` int(1) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT pour la table `Utilisateur`
--
ALTER TABLE `Utilisateur`
  MODIFY `idUtilisateur` int(2) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `Offre`
--
ALTER TABLE `Offre`
  ADD CONSTRAINT `Offre_ibfk_1` FOREIGN KEY (`idUtilisateur`) REFERENCES `Utilisateur` (`idUtilisateur`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `Offre_ibfk_3` FOREIGN KEY (`idType`) REFERENCES `Type` (`idType`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `Offre_ibfk_4` FOREIGN KEY (`idPosition`) REFERENCES `Position` (`idPosition`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
