ALTER TABLE IF EXISTS public.matches
ALTER COLUMN created_at TYPE DATE
USING created_at::date;
