CREATE TABLE [dbo].[login]
(
[email] [varchar] (40) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
[name] [varchar] (40) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
[pass] [varchar] (40) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[login] ADD CONSTRAINT [PK__login__AB6E6165234B9A26] PRIMARY KEY CLUSTERED  ([email]) ON [PRIMARY]
GO
