import axios from "axios";
import { useState } from "react";
import { Button, Col, Container, Form, Row } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const Home = () => {

    const [data, setData] = useState({ fullName: '', photoUrl: '' });
    const navigate = useNavigate();

    function enter(event) {
        event.preventDefault();
        axios.post('http://localhost:8080/api/users/save', data)
            .then(function (response) {
                let responseApi = response.data;
                if (responseApi.code === 200) {
                    navigate('/forum', { state: { userId: responseApi?.responseObject?.id, photoUrl: responseApi?.responseObject?.photoUrl } });
                    return;
                }
                toast(response?.error ?? 'Hubo un inconveniente al consultar al servidor!');
            })
            .catch(function (error) {
                // handle error
                toast(error?.data ?? 'Hubo un inconveniente al consultar al servidor!');
            })
            .finally(function () {
                // always executed
            });
    }

    const handleInputChange = (event) => {
        setData({
            ...data,
            [event.target.name] : event.target.value
        })
    }

    return <div>
        <Container>
            <Row>
                <Col className="formContainer" md={{ span: 6, offset: 3 }}>
                    <Form className="initForm w-100" onSubmit={enter.bind(this)}>
                        <Form.Group className="mb-3 w-100" controlId="formBasicEmail">
                            <Form.Label>Cuál es tu nombre?</Form.Label>
                            <Form.Control type="text" placeholder="Ingrese su nombre" name="fullName" onChange={handleInputChange.bind(this)} />
                        </Form.Group>

                        <Form.Group className="mb-3 w-100" controlId="formBasicPassword">
                            <Form.Label>Url fotografía</Form.Label>
                            <Form.Control type="text" placeholder="Url fotografía: https://d500.epimg.net/cincodias/imagenes/2016/07/04/lifestyle/1467646262_522853_1467646344_noticia_normal.jpg" name="photoUrl" onChange={handleInputChange.bind(this)} />
                        </Form.Group>
                        <Button variant="primary" type="submit">
                            Ingresar
                        </Button>
                    </Form>
                </Col>
            </Row>
        </Container>
    </div>;
};

export default Home;