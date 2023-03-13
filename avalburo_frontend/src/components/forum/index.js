/* eslint-disable react-hooks/exhaustive-deps */
import axios from "axios";
import { useEffect, useRef, useState } from "react";
import { Col, Container, FloatingLabel, Form, Row } from "react-bootstrap";
import { useLocation, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import SendImg from '../../assets/img/send.png';
import Message from "./message";

const Forum = () => {
    const location = useLocation();
    const userId = location?.state?.userId;
    const photoUrl = location?.state?.photoUrl;
    const [messageData, setMessageData] = useState({ messages: [] });
    const [data, setData] = useState({ comment: '', userId });
    const mounted = useRef(false);
    const navigate = useNavigate();

    function getMessages() {
        axios.get(`http://localhost:8080/api/messages/${userId}/findAll`)
            .then(function (response) {
                let responseApi = response.data;
                if (responseApi.code === 200) {
                    setMessageData({ messages: responseApi?.responseList ?? [] });
                    return;
                }
                setMessageData({ messages: [] });
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

    function save(event) {
        event.preventDefault();
        axios.post(`http://localhost:8080/api/messages/save`, data)
            .then(function (response) {
                let responseApi = response.data;
                if (responseApi.code === 200) {
                    getMessages();
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


    useEffect(() => {
        if (!mounted.current) {
            getMessages();
            mounted.current = true;
        }
        if (!userId) {
            navigate('/');
        }

        return () => { }
    }, []);


    return <div key={JSON.stringify(messageData.messages)}>
        <Container>
            <Row>
                <Col className="formContainer mb-5" md={{ span: 6, offset: 3 }}>
                    <FloatingLabel className="w-100" controlId="floatingTextarea2" label="Escribe la pregunta">
                        <Form.Control
                            as="textarea"
                            name="comment"
                            placeholder="Escribe la pregunta"
                            onChange={handleInputChange.bind(this)}
                            className="w-100"
                            style={{ height: '100px' }}
                        />
                        <button className="floatingButton" onClick={save.bind(this)}><img src={SendImg} alt="enviar" height={40} width={40} /></button>
                    </FloatingLabel>
                </Col>
                {
                    messageData.messages.map(message =>
                        <Message key={message?.id} message={message} messageParentId={!message?.parent ? message?.id : null} userId={userId} photoUrl={photoUrl} getMessages={getMessages.bind(this)} showButton={true}/>)
                }
            </Row>
        </Container>
    </div>;
};

export default Forum;