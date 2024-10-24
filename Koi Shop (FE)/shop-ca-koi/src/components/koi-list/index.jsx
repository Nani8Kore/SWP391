import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import "./index.scss";
import apiKoi from "../../config/koi-api";
import CardKoi from "../card-koi";
import { Button, Input, Select } from "antd";
import { Option } from "antd/es/mentions";

function KoiList() {
    const [kois, setKois] = useState([]);
    const [page, setPage] = useState(0); // Số trang hiện tại
    const [totalPages, setTotalPages] = useState(1);
    const [breeds, setBreeds] = useState([]); // State để lưu danh sách breed
    const [selectedBreed, setSelectedBreed] = useState("All");
    const [searchTerm, setSearchTerm] = useState("");
    const [name, setName] = useState([]);

    const user = useSelector((state) => state.user);
    
    

    const fetchKoi = async (page = 0) => {
        try {
            const response = await apiKoi.get(`list?page=${page}`, {
                headers: {
                    Authorization: `Bearer ${user.token}`, // Gửi token trong header
                },
            });
            setKois(response.data.content); // Lưu danh sách cá koi
            setTotalPages(response.data.totalPages); // Cập nhật tổng số trang
        } catch (e) {
            console.log(e); // Ghi lại lỗi không phải axios
        }
    };

    const fetchKoiByBreed = async (breed, page = 0) => {
        try {
            const response = await apiKoi.get(`${breed}?page=${page}`, {
                headers: {
                    Authorization: `Bearer ${user.token}`, // Gửi token trong header
                },
            });
            setKois(response.data.content); // Lưu danh sách cá koi
            setTotalPages(response.data.totalPages); // Cập nhật tổng số trang
        } catch (e) {
            console.log(e); // Ghi lại lỗi không phải axios
        }
    };

    const handleInputChange = (e) => {
        setSearchTerm(e.target.value); // Cập nhật từ khóa tìm kiếm khi người dùng nhập
    };

    const handleSearch = async () => {
        try {
            const response = await apiKoi.get(`${searchTerm}/search`, {
                headers: {
                    Authorization: `Bearer ${user.token}`,
                },
            });
            
            setName(response.data); // Cập nhật danh sách cá Koi dựa trên từ khóa tìm kiếm
            console.log(response.data);
            
            
        } catch (error) {
            console.error("Error fetching Koi by search", error);
        }
    };

    const fetchBreeds = async () => {
        try {
            const response = await apiKoi.get("http://localhost:8080/api/breeds/list-breedName", { // Giả sử API lấy danh sách breed là /breeds
                headers: {
                    Authorization: `Bearer ${user.token}`,
                },
            });
            setBreeds(response.data); // Giả sử response.data là mảng danh sách breed
        } catch (e) {
            console.log(e);
        }
    };

    useEffect(() => {
        fetchBreeds(); // Lấy danh sách breed khi component mount
        // Lấy danh sách Koi theo breed đã chọn
        if (selectedBreed === "All") {
            fetchKoi(page); // Gọi hàm fetch cho "All"
        } else {
            fetchKoiByBreed(selectedBreed, page); // Gọi hàm fetch cho breed đã chọn
        }
    }, [page, selectedBreed]);

    const handlePageChange = (newPage) => {
        setPage(newPage); // Cập nhật số trang
    };

    const handleBreedChange = (value) => {
        setSelectedBreed(value); // Cập nhật breed đã chọn
        setPage(0); // Reset về trang đầu khi thay đổi breed
    };

    return (
        <div className="koi">
            <h2>Danh sách Koi</h2>

            Search: <Input
                placeholder="Nhập tên Koi muốn tìm"
                value={searchTerm}
                onChange={handleInputChange}
                onPressEnter={handleSearch} // Gọi tìm kiếm khi nhấn Enter
                style={{ width: 300, marginBottom: "20px" }}
            />
            <Button type="primary" onClick={handleSearch}>
                Tìm kiếm
            </Button>
            
            <strong>Breed</strong><Select 
                defaultValue="All"
                style={{ width: 200, marginBottom: '20px' }}
                onChange={handleBreedChange}
            >
                <Option value="All">All</Option>
                {breeds.map((breed, index) => (
                    <Option key={index} value={breed}>
                        {breed}
                    </Option>
                ))}
            </Select>
            
            <div className="koi__list">
    {kois
        .filter(koi => name.includes(koi.fishName)) // Kiểm tra nếu fishName của koi nằm trong danh sách name
        .map((koi, index) => (
            <CardKoi key={index} koi={koi} />
        ))}
</div>

            

            <div className="koi__page">
                {Array.from({ length: totalPages }, (_, index) => (
                    <Button
                        key={index}
                        onClick={() => handlePageChange(index)}
                        style={{ margin: '0 5px', padding: '5px 10px', background: page === index ? 'lightblue' : 'white' }}
                    >
                        {index + 1}
                    </Button>
                ))}
            </div>
        </div>
    );
}

export default KoiList;
