CREATE DATABASE  IF NOT EXISTS `consultmed` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `consultmed`;

-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: consultmed
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


--
-- Table structure for table `consulta`
--

DROP TABLE IF EXISTS `consulta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consulta` (
  `idConsulta` int NOT NULL AUTO_INCREMENT,
  `dia` date DEFAULT NULL,
  `horario` time DEFAULT NULL,
  `estado` varchar(45) DEFAULT NULL,
  `idMedico` int DEFAULT NULL,
  `idPaciente` int DEFAULT NULL,
  `valor` decimal(6,2) DEFAULT NULL,
  `idUnidade` int DEFAULT NULL,
  `dataCriacao` date DEFAULT NULL,
  `dataModificacao` date DEFAULT NULL,
  PRIMARY KEY (`idConsulta`),
  KEY `FK_consulta_medico_idx` (`idMedico`),
  KEY `FK_consulta_idPaciente_idx` (`idPaciente`),
  KEY `FK_consulta_idUnidadeF_idx` (`idUnidade`),
  CONSTRAINT `FK_consulta_idMedico` FOREIGN KEY (`idMedico`) REFERENCES `medico` (`idMedico`),
  CONSTRAINT `FK_consulta_idPaciente` FOREIGN KEY (`idPaciente`) REFERENCES `pessoa` (`idPessoa`),
  CONSTRAINT `FK_consulta_idUnidadeF` FOREIGN KEY (`idUnidade`) REFERENCES `unidadef` (`idUnidadeF`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consulta`
--

LOCK TABLES `consulta` WRITE;
/*!40000 ALTER TABLE `consulta` DISABLE KEYS */;
INSERT INTO consulta (idConsulta, dia, horario, estado, idMedico, idPaciente, valor, idUnidade, dataCriacao)
VALUES
  (1, '2023-01-10', '09:00:00', 'agendado', 1, 4, 100.00, 1, '2023-01-01'),
  (2, '2023-01-11', '10:00:00', 'agendado', 1, 5, 150.00, 1, '2023-01-02'),
  (3, '2023-01-12', '11:00:00', 'agendado', 2, 6, 200.00, 1, '2023-01-03'),
  (4, '2023-01-13', '12:00:00', 'agendado', 3, 7, 200.00, 2, '2023-01-04'),
  (5, '2023-01-14', '13:00:00', 'agendado', 4, 8, 200.00, 3, '2023-01-05'),
  (6, '2023-01-15', '14:00:00', 'agendado', 4, 9, 250.00, 4, '2023-01-06');
/*!40000 ALTER TABLE `consulta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `financeiroadm`
--

DROP TABLE IF EXISTS `financeiroadm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `financeiroadm` (
  `idFinanceiroAdm` int NOT NULL AUTO_INCREMENT,
  `tipoMovimento` varchar(25) DEFAULT NULL,
  `valor` decimal(8,2) DEFAULT NULL,
  `idUnidade` int DEFAULT NULL,
  `descritivo` varchar(45) DEFAULT NULL,
  `dataCriacao` date DEFAULT NULL,
  `dataModificacao` date DEFAULT NULL,
  PRIMARY KEY (`idFinanceiroAdm`),
  KEY `FK_FinanceiroAdm_idUnidade_idx` (`idUnidade`),
  CONSTRAINT `FK_FinanceiroAdm_idUnidade` FOREIGN KEY (`idUnidade`) REFERENCES `unidadef` (`idUnidadeF`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `financeiroadm`
--

LOCK TABLES `financeiroadm` WRITE;
/*!40000 ALTER TABLE `financeiroadm` DISABLE KEYS */;
INSERT INTO financeiroadm (idFinanceiroAdm, tipoMovimento, valor, idUnidade, descritivo, dataCriacao)
VALUES
  (1, 'entrada', 100.00, 1, 'consulta', '2023-01-01'),
  (2, 'entrada', 150.00, 1, 'consulta', '2023-01-02'),
  (3, 'entrada', 200.00, 1, 'consulta', '2023-01-03'),
  (4, 'entrada', 200.00, 2, 'consulta', '2023-01-04'),
  (5, 'entrada', 200.00, 3, 'consulta', '2023-01-05'),
  (6, 'entrada', 250.00, 4, 'consulta', '2023-01-06'),
  (7, 'entrada', 100.00, 1, 'procedimento', '2023-01-01'),
  (8, 'entrada', 150.00, 1, 'procedimento', '2023-01-02'),
  (9, 'entrada', 200.00, 1, 'procedimento', '2023-01-03'),
  (10, 'entrada', 200.00, 2, 'procedimento', '2023-01-04'),
  (11, 'entrada', 200.00, 3, 'procedimento', '2023-01-05'),
  (12, 'entrada', 180.00, 4, 'procedimento', '2023-01-06');
/*!40000 ALTER TABLE `financeiroadm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `financeiromed`
--

DROP TABLE IF EXISTS `financeiromed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `financeiromed` (
  `idFinanceiroMed` int NOT NULL AUTO_INCREMENT,
  `valorMedico` decimal(8,2) DEFAULT NULL,
  `idMedico` int DEFAULT NULL,
  `estado` varchar(25) DEFAULT NULL,
  `idFranquia` int DEFAULT NULL,
  `dataCriacao` date DEFAULT NULL,
  `dataModificacao` date DEFAULT NULL,
  PRIMARY KEY (`idFinanceiroMed`),
  KEY `FK_FinanceiroMed_idMedico_idx` (`idMedico`),
  KEY `FK_FinanceiroMed_idFranquia_idx` (`idFranquia`),
  CONSTRAINT `FK_FinanceiroMed_idFranquia` FOREIGN KEY (`idFranquia`) REFERENCES `franquia` (`idFranquia`),
  CONSTRAINT `FK_FinanceiroMed_idMedico` FOREIGN KEY (`idMedico`) REFERENCES `medico` (`idMedico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `financeiromed`
--

LOCK TABLES `financeiromed` WRITE;
/*!40000 ALTER TABLE `financeiromed` DISABLE KEYS */;
/*!40000 ALTER TABLE `financeiromed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `franquia`
--

DROP TABLE IF EXISTS `franquia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `franquia` (
  `idFranquia` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  `cnpj` varchar(45) DEFAULT NULL,
  `cidade` varchar(45) DEFAULT NULL,
  `endereco` varchar(45) DEFAULT NULL,
  `idResponsavel` int DEFAULT NULL,
  `dataCriacao` date DEFAULT NULL,
  `dataModificacao` date DEFAULT NULL,
  PRIMARY KEY (`idFranquia`),
  KEY `FK_Franquia_idResponsavel_idx` (`idResponsavel`),
  CONSTRAINT `FK_Franquia_idResponsavel` FOREIGN KEY (`idResponsavel`) REFERENCES `pessoa` (`idPessoa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `franquia`
--

LOCK TABLES `franquia` WRITE;
/*!40000 ALTER TABLE `franquia` DISABLE KEYS */;
INSERT INTO franquia (idFranquia, nome, cnpj, cidade, endereco, idResponsavel, dataCriacao)
VALUES
  (1, 'Franquia A', '123456789', 'São Paulo', 'Rua A', 1, '2023-01-01'),
  (2, 'Franquia B', '987654321', 'Rio de Janeiro', 'Rua B', 3, '2023-01-02');
/*!40000 ALTER TABLE `franquia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infoconsulta`
--

DROP TABLE IF EXISTS `infoconsulta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infoconsulta` (
  `idInfoConsulta` int NOT NULL AUTO_INCREMENT,
  `idConsulta` int DEFAULT NULL,
  `descricao` varchar(45) DEFAULT NULL,
  `dataCriacao` date DEFAULT NULL,
  `dataModificacao` date DEFAULT NULL,
  PRIMARY KEY (`idInfoConsulta`),
  KEY `FK_InfoConsulta_idConsulta_idx` (`idConsulta`),
  CONSTRAINT `FK_InfoConsulta_idConsulta` FOREIGN KEY (`idConsulta`) REFERENCES `consulta` (`idConsulta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infoconsulta`
--

LOCK TABLES `infoconsulta` WRITE;
/*!40000 ALTER TABLE `infoconsulta` DISABLE KEYS */;
INSERT INTO infoconsulta (idInfoConsulta, idConsulta, descricao, dataCriacao)
VALUES
  (1, 1, 'Exame de sangue', '2023-01-01'),
  (2, 2, 'Prescrição de medicamentos', '2023-01-02'),
  (3, 3, 'Acompanhamento pós-cirúrgico', '2023-01-03'),
  (4, 4, 'Recomendações de cuidados', '2023-01-04'),
  (5, 5, 'Cirurgia de joelho', '2023-01-05'),
  (6, 6, 'Cirurgia de orelha', '2023-01-06');
/*!40000 ALTER TABLE `infoconsulta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medico`
--

DROP TABLE IF EXISTS `medico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medico` (
  `idMedico` int NOT NULL AUTO_INCREMENT,
  `crm` varchar(45) DEFAULT NULL,
  `idPessoa` int DEFAULT NULL,
  `especialidade` varchar(45) DEFAULT NULL,
  `dataCriacao` date DEFAULT NULL,
  `dataModificacao` date DEFAULT NULL,
  PRIMARY KEY (`idMedico`),
  KEY `FK_medico_idpessoa_idx` (`idPessoa`),
  CONSTRAINT `FK_medico_idpessoa` FOREIGN KEY (`idPessoa`) REFERENCES `pessoa` (`idPessoa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medico`
--

LOCK TABLES `medico` WRITE;
/*!40000 ALTER TABLE `medico` DISABLE KEYS */;
INSERT INTO medico (idMedico, crm, idPessoa, especialidade, dataCriacao)
VALUES
  (1, '1234567', 17, 'Clínico Geral', '2023-01-01'),
  (2, '9876543', 18, 'Dentista', '2023-01-02'),
  (3, '5432167', 19, 'Cardiologista', '2023-01-03'),
  (4, '7654321', 20, 'Psiquiatra', '2023-01-04');
/*!40000 ALTER TABLE `medico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pessoa`
--

DROP TABLE IF EXISTS `pessoa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pessoa` (
  `idPessoa` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  `endereco` varchar(45) DEFAULT NULL,
  `cpf` varchar(45) DEFAULT NULL,
  `telefone` varchar(45) DEFAULT NULL,
  `login` varchar(45) DEFAULT NULL,
  `senha` varchar(45) DEFAULT NULL,
  `tipoUsuario` int DEFAULT NULL,
  `dataCriacao` date DEFAULT NULL,
  `dataModificacao` date DEFAULT NULL,
  PRIMARY KEY (`idPessoa`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoa`
--

LOCK TABLES `pessoa` WRITE;
/*!40000 ALTER TABLE `pessoa` DISABLE KEYS */;
INSERT INTO pessoa (idPessoa, nome, endereco, cpf, telefone, login, senha, tipoUsuario, dataCriacao)
VALUES
  (1, 'Dono Franquia', 'Rua A', '111.222', '11', 'dono', '321', 5, '2023-01-01'),
  (2, 'Admin', 'Rua B', '333.444', '22', 'admin', '321', 6, '2023-01-02'),
  (3, 'Dono Franquia2', 'Rua D', '777.888', '55', 'dono2', '321', 5, '2023-01-03'),
  (4, 'Ana', 'Rua E', '999.111', '66', 'ana', 'senha456', 1, '2023-01-04'),
  (5, 'Fernando', 'Rua F', '222.333', '77', 'fernando', 'senha789', 1, '2023-01-05'),
  (6, 'Camila', 'Rua G', '444.555', '88', 'camila', 'senha123', 1, '2023-01-06'),
  (7, 'Paula', 'Rua H', '666.777', '99', 'paula', 'senha456', 1, '2023-01-07'),
  (8, 'Roberto', 'Rua I', '888.999', '11', 'roberto', 'senha789', 1, '2023-01-08'),
  (9, 'Lucas', 'Rua J', '111.222', '22', 'lucas', 'senha123', 1, '2023-01-09'),
  (10, 'Carolina', 'Rua K', '333.444', '33', 'carolina', 'senha456', 1, '2023-01-10'),
  (11, 'Marcos', 'Rua L', '555.666', '44', 'marcos', 'senha789', 1, '2023-01-11'),
  (12, 'Mariana', 'Rua M', '777.888', '55', 'mariana', '321', 3, '2023-01-12'),
  (13, 'dono unidade', 'Rua N', '999.111', '66', 'dono u', '123', 4, '2023-01-13'),
  (14, 'dono unidade2', 'Rua O', '222.333', '77', 'dono u2', '123', 4, '2023-01-14'),
  (15, 'dono unidade3', 'Rua P', '444.555', '88', 'dono u3', '123', 4, '2023-01-15'),
  (16, 'dono unidade4', 'Rua Q', '666.777', '99', 'dono u4', '123', 4, '2023-01-16'),
  (17, 'Fábio', 'Rua R', '888.999', '11', 'fabio', 'senha789', 2, '2023-01-17'),
  (18, 'Amanda', 'Rua S', '111.222', '22', 'amanda', 'senha123', 2, '2023-01-18'),
  (19, 'Pedro', 'Rua T', '333.444', '33', 'pedro', 'senha456', 2, '2023-01-19'),
  (20, 'Mariana', 'Rua U', '555.666', '44', 'mariana', 'senha789', 2, '2023-01-20');
/*!40000 ALTER TABLE `pessoa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `procedimento`
--

DROP TABLE IF EXISTS `procedimento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `procedimento` (
  `idProcedimento` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  `idConsulta` int DEFAULT NULL,
  `dia` date DEFAULT NULL,
  `horario` time DEFAULT NULL,
  `estado` varchar(45) DEFAULT NULL,
  `valor` decimal(6,2) DEFAULT NULL,
  `laudo` varchar(45) DEFAULT NULL,
  `dataCriacao` date DEFAULT NULL,
  `dataModificacao` date DEFAULT NULL,
  PRIMARY KEY (`idProcedimento`),
  KEY `FK_Procedimento_idConsulta_idx` (`idConsulta`),
  CONSTRAINT `FK_Procedimento_idConsulta` FOREIGN KEY (`idConsulta`) REFERENCES `consulta` (`idConsulta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `procedimento`
--

LOCK TABLES `procedimento` WRITE;
/*!40000 ALTER TABLE `procedimento` DISABLE KEYS */;
INSERT INTO procedimento (idProcedimento, nome, idConsulta, dia, horario, estado, valor, laudo, dataCriacao)
VALUES
  (1, 'Procedimento A', 1, '2023-01-01', '09:00:00', 'agendado', 100.00, 'Laudo 1', '2023-01-01'),
  (2, 'Procedimento B', 2, '2023-01-02', '10:30:00', 'agendado', 150.00, 'Laudo 2', '2023-01-02'),
  (3, 'Procedimento C', 3, '2023-01-03', '14:00:00', 'agendado', 200.00, 'Laudo 3', '2023-01-03'),
  (4, 'Procedimento D', 4, '2023-01-03', '14:00:00', 'agendado', 200.00, 'Laudo 4', '2023-01-04'),
  (5, 'Procedimento E', 5, '2023-01-03', '14:00:00', 'agendado', 200.00, 'Laudo 5', '2023-01-05'),
  (6, 'Procedimento F', 6, '2023-01-04', '16:30:00', 'agendado', 180.00, 'Laudo 6', '2023-01-06');
/*!40000 ALTER TABLE `procedimento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidadef`
--

DROP TABLE IF EXISTS `unidadef`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unidadef` (
  `idUnidadeF` int NOT NULL AUTO_INCREMENT,
  `idFranquia` int DEFAULT NULL,
  `cidade` varchar(45) DEFAULT NULL,
  `endereco` varchar(45) DEFAULT NULL,
  `idResponsavel` int DEFAULT NULL,
  `dataCriacao` date DEFAULT NULL,
  `dataModificacao` date DEFAULT NULL,
  PRIMARY KEY (`idUnidadeF`),
  KEY `FK_UnidadeF_idResponsavel_idx` (`idResponsavel`),
  KEY `FK_UnidadeF_idFranquia_idx` (`idFranquia`),
  CONSTRAINT `FK_UnidadeF_idFranquia` FOREIGN KEY (`idFranquia`) REFERENCES `franquia` (`idFranquia`),
  CONSTRAINT `FK_UnidadeF_idResponsavel` FOREIGN KEY (`idResponsavel`) REFERENCES `pessoa` (`idPessoa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidadef`
--

LOCK TABLES `unidadef` WRITE;
/*!40000 ALTER TABLE `unidadef` DISABLE KEYS */;
INSERT INTO unidadef (idUnidadeF, idFranquia, cidade, endereco, idResponsavel, dataCriacao)
VALUES
  (1, 1, 'São Paulo', 'Rua A', 13, '2023-01-01'),
  (2, 1, 'Rio de Janeiro', 'Rua B', 14, '2023-01-02'),
  (3, 2, 'Belo Horizonte', 'Rua C', 15, '2023-01-03'),
  (4, 2, 'Curitiba', 'Rua D', 16, '2023-01-04');
/*!40000 ALTER TABLE `unidadef` ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-25  6:52:31
