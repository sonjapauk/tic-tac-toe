DO $$
BEGIN
   IF EXISTS (
       SELECT 1
       FROM information_schema.tables
       WHERE table_schema = 'public' AND table_name = 'users'
   ) THEN
       UPDATE users
       SET roles = '["USER"]'
       WHERE roles IS NULL OR roles = '';
   END IF;
END $$;
