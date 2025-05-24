-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : sam. 24 mai 2025 à 11:32
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `annotation`
--

-- --------------------------------------------------------

--
-- Structure de la table `assignments`
--

CREATE TABLE `assignments` (
  `id` bigint(20) NOT NULL,
  `annotated_at` datetime(6) DEFAULT NULL,
  `label_id` bigint(20) DEFAULT NULL,
  `text_entry_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `assignments`
--

INSERT INTO `assignments` (`id`, `annotated_at`, `label_id`, `text_entry_id`, `user_id`) VALUES
(1, '2025-05-23 22:36:45.000000', 1, 3, 4),
(2, '2025-05-23 22:37:02.000000', 2, 6, 4),
(3, '2025-05-23 22:37:08.000000', 1, 9, 4),
(4, '2025-05-23 22:39:20.000000', 3, 12, 4),
(5, '2025-05-23 22:39:25.000000', 1, 15, 4),
(6, '2025-05-23 22:39:32.000000', 3, 18, 4),
(7, '2025-05-23 23:23:41.000000', 8, 27, 4),
(8, '2025-05-23 23:23:45.000000', 8, 29, 4),
(9, '2025-05-23 22:39:59.000000', 6, 31, 4),
(10, '2025-05-23 22:40:04.000000', 6, 33, 4),
(11, '2025-05-23 22:41:31.000000', 2, 1, 2),
(12, '2025-05-23 22:41:34.000000', 2, 4, 2),
(13, '2025-05-23 22:41:48.000000', 2, 7, 2),
(14, '2025-05-23 22:41:53.000000', 2, 10, 2),
(15, '2025-05-23 22:42:00.000000', 2, 13, 2),
(16, '2025-05-23 22:42:06.000000', 1, 16, 2),
(17, '2025-05-23 22:42:42.000000', 4, 21, 2),
(18, '2025-05-23 22:42:58.000000', 8, 26, 2),
(19, '2025-05-23 22:44:03.000000', 2, 2, 3),
(20, '2025-05-23 22:44:09.000000', 1, 5, 3),
(21, '2025-05-23 22:44:15.000000', 3, 8, 3),
(22, '2025-05-23 22:44:34.000000', 3, 11, 3),
(23, '2025-05-23 22:44:40.000000', 3, 14, 3),
(24, '2025-05-23 22:44:46.000000', 3, 17, 3),
(25, '2025-05-23 22:44:52.000000', 2, 20, 3),
(26, '2025-05-23 22:49:18.000000', 1, 19, 4),
(27, '2025-05-23 23:23:06.000000', 13, 66, 4),
(28, '2025-05-23 23:23:12.000000', 13, 67, 4),
(29, '2025-05-23 23:23:58.000000', 8, 43, 4),
(30, '2025-05-24 09:23:31.000000', 15, 87, 4),
(31, '2025-05-24 09:23:40.000000', 17, 88, 4),
(32, '2025-05-24 10:04:32.000000', 5, 22, 4),
(33, '2025-05-24 10:04:43.000000', 4, 24, 4);

-- --------------------------------------------------------

--
-- Structure de la table `labels`
--

CREATE TABLE `labels` (
  `id` bigint(20) NOT NULL,
  `label_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `labels`
--

INSERT INTO `labels` (`id`, `label_name`) VALUES
(1, 'similaire'),
(2, 'neutre'),
(3, 'proche'),
(4, 'positif'),
(5, 'negatif'),
(6, 'similaire'),
(7, 'neutre'),
(8, 'proche'),
(9, 'similaire'),
(10, 'neutre'),
(11, 'proche'),
(12, 'similaire'),
(13, 'neutre'),
(14, 'proche'),
(15, 'similaire'),
(16, 'neutre'),
(17, 'proche'),
(18, 'similaire'),
(19, 'neutre'),
(20, 'proche');

-- --------------------------------------------------------

--
-- Structure de la table `tasks`
--

CREATE TABLE `tasks` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `csv_file_path` varchar(255) DEFAULT NULL,
  `date_limite` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `tasks`
--

INSERT INTO `tasks` (`id`, `created_at`, `csv_file_path`, `date_limite`, `description`, `title`) VALUES
(1, '2025-05-23 22:34:00.000000', 'C:/Users/INFO/Documents/workspace-spring-tool-suite-4-4.28.0.RELEASE/registration-login-springboot-security-thymeleaf/uploads/textes_a_annoter (1).csv', '2025-05-31 22:33:00.000000', 'tache a annoter ', 'Tache 1 '),
(2, '2025-05-23 22:35:05.000000', 'C:/Users/INFO/Documents/workspace-spring-tool-suite-4-4.28.0.RELEASE/registration-login-springboot-security-thymeleaf/uploads/annotation_data.csv', '2025-06-21 22:33:00.000000', 'tache a annoter tres rapide ', 'Tache 2'),
(3, '2025-05-23 22:36:07.000000', 'C:/Users/INFO/Documents/workspace-spring-tool-suite-4-4.28.0.RELEASE/registration-login-springboot-security-thymeleaf/uploads/textes_a_annoter (1).csv', '2025-05-31 22:36:00.000000', 'tache 3 ', 'tache 3 '),
(4, '2025-05-23 22:47:04.000000', 'C:/Users/INFO/Documents/workspace-spring-tool-suite-4-4.28.0.RELEASE/registration-login-springboot-security-thymeleaf/uploads/textes_a_annoter (1).csv', '2025-05-31 22:47:00.000000', 'c est la tache 4 ', 'tache 4 '),
(5, '2025-05-23 23:17:48.000000', 'C:/Users/INFO/Documents/workspace-spring-tool-suite-4-4.28.0.RELEASE/registration-login-springboot-security-thymeleaf/uploads/textes_a_annoter (1).csv', '2025-05-31 23:17:00.000000', 'qewjrkwekr', 'tache 6 '),
(6, '2025-05-24 09:18:18.000000', 'C:/Users/INFO/Documents/workspace-spring-tool-suite-4-4.28.0.RELEASE/registration-login-springboot-security-thymeleaf/uploads/textes_a_annoter (1).csv', '2025-05-30 09:18:00.000000', 'eryreuu', 'tache 8 '),
(7, '2025-05-24 10:00:51.000000', 'C:/Users/INFO/Documents/workspace-spring-tool-suite-4-4.28.0.RELEASE/registration-login-springboot-security-thymeleaf/uploads/textes_a_annoter (1).csv', '2025-05-31 10:00:00.000000', 'ewuwuiu', 'tache 9 ');

-- --------------------------------------------------------

--
-- Structure de la table `task_labels`
--

CREATE TABLE `task_labels` (
  `task_id` bigint(20) NOT NULL,
  `label_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `task_labels`
--

INSERT INTO `task_labels` (`task_id`, `label_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 4),
(2, 5),
(3, 6),
(3, 7),
(3, 8),
(4, 9),
(4, 10),
(4, 11),
(5, 12),
(5, 13),
(5, 14),
(6, 15),
(6, 16),
(6, 17),
(7, 18),
(7, 19),
(7, 20);

-- --------------------------------------------------------

--
-- Structure de la table `text_entries`
--

CREATE TABLE `text_entries` (
  `id` bigint(20) NOT NULL,
  `assigned` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `num_ligne` int(11) NOT NULL,
  `task_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `text_entries`
--

INSERT INTO `text_entries` (`id`, `assigned`, `created_at`, `num_ligne`, `task_id`, `user_id`) VALUES
(1, b'0', '2025-05-23 22:34:01.000000', 1, 1, 2),
(2, b'0', '2025-05-23 22:34:01.000000', 2, 1, 3),
(3, b'0', '2025-05-23 22:34:01.000000', 3, 1, 4),
(4, b'0', '2025-05-23 22:34:01.000000', 4, 1, 2),
(5, b'0', '2025-05-23 22:34:01.000000', 5, 1, 3),
(6, b'0', '2025-05-23 22:34:01.000000', 6, 1, 4),
(7, b'0', '2025-05-23 22:34:01.000000', 7, 1, 2),
(8, b'0', '2025-05-23 22:34:01.000000', 8, 1, 3),
(9, b'0', '2025-05-23 22:34:01.000000', 9, 1, 4),
(10, b'0', '2025-05-23 22:34:01.000000', 10, 1, 2),
(11, b'0', '2025-05-23 22:34:01.000000', 11, 1, 3),
(12, b'0', '2025-05-23 22:34:01.000000', 12, 1, 4),
(13, b'0', '2025-05-23 22:34:01.000000', 13, 1, 2),
(14, b'0', '2025-05-23 22:34:01.000000', 14, 1, 3),
(15, b'0', '2025-05-23 22:34:01.000000', 15, 1, 4),
(16, b'0', '2025-05-23 22:34:01.000000', 16, 1, 2),
(17, b'0', '2025-05-23 22:34:01.000000', 17, 1, 3),
(18, b'0', '2025-05-23 22:34:01.000000', 18, 1, 4),
(19, b'0', '2025-05-23 22:34:01.000000', 19, 1, 4),
(20, b'0', '2025-05-23 22:34:01.000000', 20, 1, 3),
(21, b'0', '2025-05-23 22:35:05.000000', 1, 2, 2),
(22, b'0', '2025-05-23 22:35:05.000000', 2, 2, 4),
(23, b'0', '2025-05-23 22:35:05.000000', 3, 2, 2),
(24, b'0', '2025-05-23 22:35:05.000000', 4, 2, 4),
(25, b'0', '2025-05-23 22:35:05.000000', 5, 2, 2),
(26, b'0', '2025-05-23 22:36:07.000000', 1, 3, 2),
(27, b'0', '2025-05-23 22:36:07.000000', 2, 3, 4),
(28, b'0', '2025-05-23 22:36:07.000000', 3, 3, 2),
(29, b'0', '2025-05-23 22:36:07.000000', 4, 3, 4),
(30, b'0', '2025-05-23 22:36:07.000000', 5, 3, 2),
(31, b'0', '2025-05-23 22:36:07.000000', 6, 3, 4),
(32, b'0', '2025-05-23 22:36:07.000000', 7, 3, 2),
(33, b'0', '2025-05-23 22:36:07.000000', 8, 3, 4),
(34, b'0', '2025-05-23 22:36:07.000000', 9, 3, 2),
(35, b'0', '2025-05-23 22:36:07.000000', 10, 3, 4),
(36, b'0', '2025-05-23 22:36:07.000000', 11, 3, 2),
(37, b'0', '2025-05-23 22:36:07.000000', 12, 3, 4),
(38, b'0', '2025-05-23 22:36:07.000000', 13, 3, 2),
(39, b'0', '2025-05-23 22:36:07.000000', 14, 3, 4),
(40, b'0', '2025-05-23 22:36:07.000000', 15, 3, 2),
(41, b'0', '2025-05-23 22:36:07.000000', 16, 3, 4),
(42, b'0', '2025-05-23 22:36:07.000000', 17, 3, 2),
(43, b'0', '2025-05-23 22:36:07.000000', 18, 3, 4),
(44, b'0', '2025-05-23 22:36:07.000000', 19, 3, 2),
(45, b'0', '2025-05-23 22:36:07.000000', 20, 3, 4),
(46, b'0', '2025-05-23 22:47:05.000000', 1, 4, 2),
(47, b'0', '2025-05-23 22:47:05.000000', 2, 4, 3),
(48, b'0', '2025-05-23 22:47:05.000000', 3, 4, 3),
(49, b'0', '2025-05-23 22:47:05.000000', 4, 4, 3),
(50, b'0', '2025-05-23 22:47:05.000000', 5, 4, 3),
(51, b'0', '2025-05-23 22:47:05.000000', 6, 4, 2),
(52, b'0', '2025-05-23 22:47:05.000000', 7, 4, 3),
(53, b'0', '2025-05-23 22:47:05.000000', 8, 4, 2),
(54, b'0', '2025-05-23 22:47:05.000000', 9, 4, 2),
(55, b'0', '2025-05-23 22:47:05.000000', 10, 4, 3),
(56, b'0', '2025-05-23 22:47:05.000000', 11, 4, 3),
(57, b'0', '2025-05-23 22:47:05.000000', 12, 4, 2),
(58, b'0', '2025-05-23 22:47:05.000000', 13, 4, 3),
(59, b'0', '2025-05-23 22:47:05.000000', 14, 4, 3),
(60, b'0', '2025-05-23 22:47:05.000000', 15, 4, 2),
(61, b'0', '2025-05-23 22:47:05.000000', 16, 4, 3),
(62, b'0', '2025-05-23 22:47:05.000000', 17, 4, 2),
(63, b'0', '2025-05-23 22:47:05.000000', 18, 4, 2),
(64, b'0', '2025-05-23 22:47:05.000000', 19, 4, 2),
(65, b'0', '2025-05-23 22:47:05.000000', 20, 4, 2),
(66, b'0', '2025-05-23 23:17:48.000000', 1, 5, 4),
(67, b'0', '2025-05-23 23:17:48.000000', 2, 5, 4),
(68, b'0', '2025-05-23 23:17:48.000000', 3, 5, 2),
(69, b'0', '2025-05-23 23:17:48.000000', 4, 5, 4),
(70, b'0', '2025-05-23 23:17:48.000000', 5, 5, 4),
(71, b'0', '2025-05-23 23:17:48.000000', 6, 5, 4),
(72, b'0', '2025-05-23 23:17:48.000000', 7, 5, 2),
(73, b'0', '2025-05-23 23:17:48.000000', 8, 5, 4),
(74, b'0', '2025-05-23 23:17:48.000000', 9, 5, 2),
(75, b'0', '2025-05-23 23:17:48.000000', 10, 5, 4),
(76, b'0', '2025-05-23 23:17:48.000000', 11, 5, 4),
(77, b'0', '2025-05-23 23:17:48.000000', 12, 5, 4),
(78, b'0', '2025-05-23 23:17:48.000000', 13, 5, 2),
(79, b'0', '2025-05-23 23:17:48.000000', 14, 5, 4),
(80, b'0', '2025-05-23 23:17:48.000000', 15, 5, 4),
(81, b'0', '2025-05-23 23:17:48.000000', 16, 5, 4),
(82, b'0', '2025-05-23 23:17:48.000000', 17, 5, 4),
(83, b'0', '2025-05-23 23:17:48.000000', 18, 5, 4),
(84, b'0', '2025-05-23 23:17:48.000000', 19, 5, 2),
(85, b'0', '2025-05-23 23:17:48.000000', 20, 5, 4),
(86, b'0', '2025-05-24 09:18:19.000000', 1, 6, 2),
(87, b'0', '2025-05-24 09:18:19.000000', 2, 6, 4),
(88, b'0', '2025-05-24 09:18:19.000000', 3, 6, 4),
(89, b'0', '2025-05-24 09:18:19.000000', 4, 6, 2),
(90, b'0', '2025-05-24 09:18:19.000000', 5, 6, 2),
(91, b'0', '2025-05-24 09:18:19.000000', 6, 6, 2),
(92, b'0', '2025-05-24 09:18:19.000000', 7, 6, 2),
(93, b'0', '2025-05-24 09:18:19.000000', 8, 6, 2),
(94, b'0', '2025-05-24 09:18:19.000000', 9, 6, 2),
(95, b'0', '2025-05-24 09:18:19.000000', 10, 6, 2),
(96, b'0', '2025-05-24 09:18:19.000000', 11, 6, 2),
(97, b'0', '2025-05-24 09:18:19.000000', 12, 6, 2),
(98, b'0', '2025-05-24 09:18:19.000000', 13, 6, 2),
(99, b'0', '2025-05-24 09:18:19.000000', 14, 6, 2),
(100, b'0', '2025-05-24 09:18:19.000000', 15, 6, 2),
(101, b'0', '2025-05-24 09:18:19.000000', 16, 6, 2),
(102, b'0', '2025-05-24 09:18:19.000000', 17, 6, 2),
(103, b'0', '2025-05-24 09:18:19.000000', 18, 6, 2),
(104, b'0', '2025-05-24 09:18:19.000000', 19, 6, 2),
(105, b'0', '2025-05-24 09:18:19.000000', 20, 6, 2),
(106, b'0', '2025-05-24 10:00:51.000000', 1, 7, 2),
(107, b'0', '2025-05-24 10:00:51.000000', 2, 7, 2),
(108, b'0', '2025-05-24 10:00:51.000000', 3, 7, 2),
(109, b'0', '2025-05-24 10:00:52.000000', 4, 7, 2),
(110, b'0', '2025-05-24 10:00:52.000000', 5, 7, 2),
(111, b'0', '2025-05-24 10:00:52.000000', 6, 7, 2),
(112, b'0', '2025-05-24 10:00:52.000000', 7, 7, 2),
(113, b'0', '2025-05-24 10:00:52.000000', 8, 7, 2),
(114, b'0', '2025-05-24 10:00:52.000000', 9, 7, 2),
(115, b'0', '2025-05-24 10:00:52.000000', 10, 7, 2),
(116, b'0', '2025-05-24 10:00:52.000000', 11, 7, 2),
(117, b'0', '2025-05-24 10:00:52.000000', 12, 7, 2),
(118, b'0', '2025-05-24 10:00:52.000000', 13, 7, 2),
(119, b'0', '2025-05-24 10:00:52.000000', 14, 7, 2),
(120, b'0', '2025-05-24 10:00:52.000000', 15, 7, 2),
(121, b'0', '2025-05-24 10:00:52.000000', 16, 7, 2),
(122, b'0', '2025-05-24 10:00:52.000000', 17, 7, 2),
(123, b'0', '2025-05-24 10:00:52.000000', 18, 7, 2),
(124, b'0', '2025-05-24 10:00:52.000000', 19, 7, 2),
(125, b'0', '2025-05-24 10:00:52.000000', 20, 7, 2);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `is_deleted` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `email`, `is_deleted`, `name`, `password`, `role`) VALUES
(1, 'bellafatimazahrae@gmail.com', b'0', 'FATIMA ZOHRA BELLA', '$2a$10$ap/hPEHCTm.j77WmTIbCc.LGa3MaLGcqWesTkm8pliDkAvXkoxOW.', 'ROLE_ADMIN'),
(2, 'rajae@gmail.com', b'0', 'Rajae EL KHANTACH', '$2a$10$5G.Ic.bFJkD7tFsoPdcDt.SMd6qjVqrYS5nhzMhwYznPiOw8UBuxC', 'ROLE_USER'),
(3, 'fatima@gmail.com', b'0', 'fatima  lougmiri', '$2a$10$rHaA4Zdoo7fUo7tkgOcyM.T5aSXqIOFGZQz9qLOqQ8cA0U8S8LhFe', 'ROLE_USER'),
(4, 'aya@gmail.com', b'0', 'Aya BELLA', '$2a$10$fyc7H1H50l2R1ASUiXrVq.y7qwoz9AUem.i.6TPPBPs/XOaw3OlMC', 'ROLE_USER');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `assignments`
--
ALTER TABLE `assignments`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKhumvaf7i7hrfqruhx3hkn6x07` (`text_entry_id`),
  ADD KEY `FKqkelmh3oski6gp36hrjxoeb6` (`label_id`),
  ADD KEY `FK8iy60sfbpptmtm10f4al96qvq` (`user_id`);

--
-- Index pour la table `labels`
--
ALTER TABLE `labels`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `tasks`
--
ALTER TABLE `tasks`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `task_labels`
--
ALTER TABLE `task_labels`
  ADD PRIMARY KEY (`task_id`,`label_id`),
  ADD KEY `FKlr49cbsj797rym78wepiid0sh` (`label_id`);

--
-- Index pour la table `text_entries`
--
ALTER TABLE `text_entries`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKe0f46rh3j5oixki23dslfrt6g` (`task_id`),
  ADD KEY `FKdd3n1kdv1ucrlcqfxep924ym3` (`user_id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `assignments`
--
ALTER TABLE `assignments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT pour la table `labels`
--
ALTER TABLE `labels`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT pour la table `tasks`
--
ALTER TABLE `tasks`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `text_entries`
--
ALTER TABLE `text_entries`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=126;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `assignments`
--
ALTER TABLE `assignments`
  ADD CONSTRAINT `FK194o7sk4ux2fsksmc47b0leeg` FOREIGN KEY (`text_entry_id`) REFERENCES `text_entries` (`id`),
  ADD CONSTRAINT `FK8iy60sfbpptmtm10f4al96qvq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKqkelmh3oski6gp36hrjxoeb6` FOREIGN KEY (`label_id`) REFERENCES `labels` (`id`);

--
-- Contraintes pour la table `task_labels`
--
ALTER TABLE `task_labels`
  ADD CONSTRAINT `FK7wi3dfqb8gx9kiysuy980sbus` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`),
  ADD CONSTRAINT `FKlr49cbsj797rym78wepiid0sh` FOREIGN KEY (`label_id`) REFERENCES `labels` (`id`);

--
-- Contraintes pour la table `text_entries`
--
ALTER TABLE `text_entries`
  ADD CONSTRAINT `FKdd3n1kdv1ucrlcqfxep924ym3` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKe0f46rh3j5oixki23dslfrt6g` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
