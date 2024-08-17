[E-Commerce System Design]


![E-Commerce System Design](ecom.jpg)

Product service is the main products catalog service which serves the user requests about browsing and searching the desired products.

Primary functionalities provided by the Product Service:
a.	Get all the products in the system (using Paging)
b.	Get specific products
c.	Add new products in the e-commerce system
d.	Update an existing product
e.	Remove particular products in the system

Products & Category API endpoints:

1. Get products and categories:
	GET /products
	GET	/products/1
	GET /categories
	GET	/categories/1

2. Add new product:
	POST /products
	
	body:
	{
		"title": "Mountanins t-shirt",
		"description": "ZARA T-shirt",
		"price": 2500,
		"imageUrl": "https://someimagerepo.com/img/Mountaninst-shirt.jpg",
		"category": {
			"name": "T-Shirts"
		}
	}
	
3. Delete a product:
	DELETE /products/1

4. Replace a product:
	PUT /products/1
	
	body:
	{
		"title": "Mountanins t-shirt",
		"description": "ZARA T-shirt",
		"price": 2500,
		"imageUrl": "https://someimagerepo.com/img/Mountaninst-shirt.jpg",
		"category": {
			"name": "T-Shirts"
		}
	}
	
5. Update a product:
	PATCH /products/1
	
	body:
	{
		"description": "ZARA T-shirt Limited Edition",
		"price": 9999,
	}
