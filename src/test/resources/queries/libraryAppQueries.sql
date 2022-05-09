

-- us 01

select id from users;

-- us02

select * from book_borrow;
select count(*) from book_borrow where is_returned=0;


-- us 03 --

select bc.name,count(*) from book_borrow bb inner join books b on bb.book_id = b.id
inner join book_categories bc on b.book_category_id = bc.id
group by bc.name
order by 2 desc ;


-- us 04--

select full_name,count(*) from users u inner join book_borrow bb on u.id = bb.user_id
group by full_name
order by 2 desc ;
;


-- us05 --
select name, author, year from books where name='Chordeiles minor';


-- us 06 --
select name from book_categories;

