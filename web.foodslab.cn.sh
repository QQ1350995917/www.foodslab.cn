cd src/main/webapp/webapp/asserts

rm *.css
rm *.js

echo "=============================relaese============================="
echo "relaese.css"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/release/webapp/asserts/frame-all-release.css
echo "relaese.js"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/release/webapp/asserts/frame-all-release.js

echo "=============================application============================="
echo "application.css"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/application.css
echo "application.js"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/application.js

echo "=============================index============================="
echo "index.css"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/index.css
echo "index.js"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/index.js

echo "=============================series============================="
echo "series.css"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/series.css
echo "series.js"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/series.js

echo "=============================detail============================="
echo "detail.css"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/detail.css
echo "detail.js"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/detail.js

echo "=============================billing============================="
echo "billing.css"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/billing.css
echo "billing.js"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/billing.js
echo "receiver.css"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/receiver.css
echo "receiver.js"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/receiver.js
echo "payment.js"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/payment.js

echo "=============================receiver============================="
echo "receiver.css"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/receiver.css
echo "receiver.js"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/receiver.js


echo "=============================query============================="
echo "query.css"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/query.css
echo "query.js"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/query.js


echo "=============================toast============================="
echo "toast.js"
curl -O https://raw.githubusercontent.com/QQ1350995917/web.foodslab.cn/master/src/frontend/asserts/toast.js




#cd ../widgets/
#rm frame.html
#curl -O https://github.com/QQ1350995917/web.foodslab.cn/tree/master/src/webapp/widgets/frame.html
