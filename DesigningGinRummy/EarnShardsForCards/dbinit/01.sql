CREATE DATABASE IF NOT EXISTS `portfolio_main`;
GRANT ALL ON `portfolio_main`.* TO 'portfolio_admin'@'%';

CREATE DATABASE IF NOT EXISTS `portfolio_logs`;
GRANT ALL ON `portfolio_logs`.* TO 'portfolio_admin'@'%';

CREATE DATABASE IF NOT EXISTS `portfolio_main_test`;
GRANT ALL ON `portfolio_main_test`.* TO 'portfolio_admin'@'%';

CREATE DATABASE IF NOT EXISTS `portfolio_logs_test`;
GRANT ALL ON `portfolio_logs_test`.* TO 'portfolio_admin'@'%';