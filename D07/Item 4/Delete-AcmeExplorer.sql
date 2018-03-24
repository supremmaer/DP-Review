start transaction;
use `acmeexplorer`;
revoke all privileges on `acmeexplorer`.* from 'acme-user'@'%';
revoke all privileges on `acmeexplorer`.* from 'acme-manager'@'%';
drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';
drop database `acmeexplorer`;
commit;