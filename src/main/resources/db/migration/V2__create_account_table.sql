CREATE TABLE account
(
    account_id         UUID DEFAULT gen_random_uuid(),
    account_balance    DECIMAL(9, 2),
    account_created_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT pk_account_id PRIMARY KEY (account_id)
);