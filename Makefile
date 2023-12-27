dev:
	npm install --global nodemon
	npm install
	nodemon \
	  --watch routes \
	  --watch sync.js \
	  --watch aidbox-resources.yaml \
	  -e yaml,clj,html,css,js sync.js
