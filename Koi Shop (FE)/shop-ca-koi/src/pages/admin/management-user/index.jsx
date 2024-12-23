import { Table, Button } from "antd";
import Dashboard from "../../../components/dashboard";
import { useEffect, useState } from "react";
import axios from "axios";
import { useSelector } from "react-redux";
import { useNavigate, Link } from "react-router-dom";

function User() {
  const [dataSource, setDataSource] = useState([]); 
  const user = useSelector((state) => state.user);
  const navigate = useNavigate(); 

  
  async function loadUserList() {
    try {
      const response = await axios.get(
        "http://14.225.210.143:8080/api/user/list-user",
        {
          headers: {
            Authorization: `Bearer ${user.token}`,
          },
        }
      );
      
      setDataSource(response.data);
    } catch (error) {
      console.error("Error fetching user list:", error);
    }
  }

  
  useEffect(() => {
    loadUserList();
  }, []); 

  
  const deleteUser = async (userID) => {
    try {
      const response = await axios.delete(
        `http://14.225.210.143:8080/api/user/${userID}/delete`,
        {
          headers: {
            Authorization: `Bearer ${user.token}`,
          },
        }
      );
      
      if (response.status === 200 || response.status === 204) {
        
        setDataSource((prevDataSource) =>
          prevDataSource.filter((user) => user.id !== userID)
        );
      } else {
        console.error(
          "Failed to delete user. Response status:",
          response.status
        );
      }
    } catch (error) {
      console.error("Error deleting user:", error);
    }
  };

  
  const viewUserDetails = (userID) => {
    navigate(`/home/dashboard/user/${userID}/detail`);
  };

  const columns = [
    {
      title: "User ID",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "Username",
      dataIndex: "userName",
      key: "userName",
    },
    {
      title: "Join Date",
      dataIndex: "joinDate",
      key: "joinDate",
      render: (joinDate) => new Date(joinDate).toLocaleDateString(),
    },
    {
      title: "Role",
      dataIndex: "role",
      key: "role",
    },
    {
      title: "Actions",
      key: "actions",
      render: (text, record) => (
        <div>
          <Link to={`/home/dashboard/user/${record.id}/detail`}>
            <Button type="default">Detail</Button>
          </Link>
          <Button
            type="default"
            onClick={() => deleteUser(record.id)}
            style={{ marginLeft: "8px" }}
          >
            Delete
          </Button>
        </div>
      ),
    },
  ];

  return (
    <div>
      <Dashboard>
        <Table dataSource={dataSource} columns={columns} rowKey="id" />
      </Dashboard>
    </div>
  );
}

export default User;
