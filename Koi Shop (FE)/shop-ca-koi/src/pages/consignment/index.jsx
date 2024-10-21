import axios from "axios";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { Table, Spin, Alert } from "antd";

function Consignment() {
  const user = useSelector((state) => state.user);
  const [consignments, setConsignments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchConsignment = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/api/consignments/getforCustomer",
          {
            headers: {
              Authorization: `Bearer ${user.token}`, // Gửi token trong header
            },
          }
        );
        setConsignments(response.data);
        setLoading(false);
      } catch (error) {
        setError("Không thể tải dữ liệu consignment.");
        setLoading(false);
      }
    };

    fetchConsignment();
  }, [user.token]);

  const columns = [
    {
      title: "ID",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "Tên cá",
      dataIndex: "fishName",
      key: "fishName",
    },
    {
      title: "Ngày yêu cầu",
      dataIndex: "requestDate",
      key: "requestDate",
      render: (text) => new Date(text).toLocaleString(),
    },
    {
      title: "Loại ký gửi",
      dataIndex: "consignmentType",
      key: "consignmentType",
    },
    {
      title: "Trạng thái",
      dataIndex: "status",
      key: "status",
      render: (status) => (status ? "Hoàn thành" : "Chưa hoàn thành"),
    },
    {
      title: "Giá bán tại cửa hàng",
      dataIndex: "shopPrice",
      key: "shopPrice",
      render: (price) => `${price} VND`,
    },
  ];

  if (loading) {
    return <Spin tip="Đang tải..." />;
  }

  if (error) {
    return <Alert message="Lỗi" description={error} type="error" showIcon />;
  }

  return (
    <div style={{ padding: "20px" }}>
      <h1>Danh sách ký gửi</h1>
      <Table
        columns={columns}
        dataSource={consignments}
        rowKey={(record) => record.id}
        bordered
      />
    </div>
  );
}

export default Consignment;