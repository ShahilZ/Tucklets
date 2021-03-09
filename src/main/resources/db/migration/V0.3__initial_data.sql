-- Recreate admin table so admin_id column stays first
drop table admin;

CREATE TABLE admin
(
    -- changed from bigint to UUID
    admin_id UUID NOT NULL,
    creation_date date NOT NULL,
    deletion_date date,
    enabled boolean NOT NULL,
    encrypted_password varchar(255) NOT NULL,
    last_login_date date NOT NULL,
    username varchar(255) NOT NULL,
    PRIMARY KEY (admin_id)
);

-- Initialize with default admin. Update password before going live
insert INTO admin(
	admin_id,
	creation_date,
	deletion_date,
	enabled,
	encrypted_password,
	last_login_date,
	username)
values (
	'048d4be1-cdb0-46fd-9577-311ad58ec7f1',
	'2021-03-13',
	null,
	true,
	'$2a$12$.kxg5AMNoIcK/M6Da.pCeeYJnQos09TBJUYV1/ANBt.iVFfFg4LMS',
	'2021-03-13',
	'admin'
);