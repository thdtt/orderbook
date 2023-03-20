# ORDER BOOK

## 1. Cấu trúc dữ liệu
- Đối với OrderBook model, em sử dụng cấu trúc dữ liệu TreeMap của Java để build lên danh sách bid offers và ask offers (Trong đó bid offer là danh sách các lệnh mua và ask offers là danh sách các lệnh bán)
- Cấu trúc dữ liệu TreeMap của Java bản chất là một cây đỏ đen Red-Black Tree (cây cân bằng, độ cao cây con bên trái và cây con bên phải không chênh nhau quá 1 đơn vị) và được implement SortedMap (Map được sắp xếp)
- Việc sử dụng TreeMap đem lại lợi ích cho việc tìm kiếm và chèn đối tượng khi tìm kiếm và chèn đều mất OlogN
## 2. Thuật toán sử dụng
- Matching engine được sử dụng với dạng đơn giản khi cấu trúc dữ liệu TreeMap đã implement sẵn SortedMap
- Do bản thân TreeMap implement SortedMap nên các phần tử chèn vào trong danh sách luôn được sắp xếp theo thứ tự, việc sắp xếp theo thứ tự giúp ranking được các offers, giúp giảm thiểu tính toán khi tìm kiếm các offer tốt nhất đối với một order mới được đẩy vào
- Bid offers là các lệnh bán, cho nên bid offers sẽ được sắp xếp theo thứ tự giảm dần để các offers với mức giá mua cao nhất sẽ được sắp xếp ở đầu danh sách
- Ask offers là các lệnh mua, cho nên ask offers sẽ được sắp xếp theo thứ tự tăng dần để các offers với mức giá bán thấp nhất sẽ được sắp xếp ở đầu danh sách
## 3. Implementation