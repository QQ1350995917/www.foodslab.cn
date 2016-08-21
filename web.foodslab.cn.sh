cd src/main/webapp/webapp/asserts

rm *.css
rm *.js

curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/release/webapp/asserts/frame-all-release.css
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/release/webapp/asserts/frame-all-release.js

curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/application.css
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/application.js
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/index.css
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/index.js
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/series.css
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/series.js


#cd ../widgets/
#rm frame.html
#curl -O https://github.com/QQ1350995917/web.foodslab.cn/tree/master/src/webapp/widgets/frame.html
