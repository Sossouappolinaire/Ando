create table if not exists operations (id text primary key, date date not null, sim text not null check (sim in ('MTN','Moov','Celtis')), type text not null check (type in ('dep','ret','cred')), amount numeric not null, profit numeric not null default 0, frais numeric default 0, ref text, source text default 'sms', created_at timestamptz default now());
create unique index if not exists operations_sim_ref on operations (sim, ref) where ref is not null;
create table if not exists parametres (id int primary key default 1 check (id = 1), data jsonb not null default '{}');
alter table operations enable row level security;
alter table parametres enable row level security;
drop policy if exists "acces connecte" on operations;
drop policy if exists "acces connecte" on parametres;
create policy "acces connecte" on operations for all to authenticated using (true) with check (true);
create policy "acces connecte" on parametres for all to authenticated using (true) with check (true);
