import moment from "moment/moment";
import { useState } from "react";
import { Button, Col, Row } from "react-bootstrap";
import Avatar from '../../../assets/img/avatar_default.jpg';
import ResponseForm from "./responseForm";

const Message = ({ message, messageParentId, userId, photoUrl, getMessages }) => {
    const [openInput, setOpenInput] = useState(false);

    const printTime = () => {
        const diff1 = moment.duration(moment().diff(moment(message?.createdAt))).asSeconds();

        let timeAgo;

        if (diff1 < 60) {
            timeAgo = `Hace ${Math.floor(diff1)} segundos`;
        } else if (diff1 < 3600) {
            timeAgo = `Hace ${Math.floor(diff1 / 60)} minutos`;
        } else if (diff1 < 216000) {
            timeAgo = `Hace ${Math.floor(diff1 / 3600)} horas`;
        } else {
            timeAgo = moment(diff1).fromNow();
        }

        return <>{timeAgo}</>;
    }

    return <>
        <Col className="card mt-2" md={{ span: 6, offset: 3 }}>
            <Row className="p-1">
                <Col>
                    <img src={photoUrl ?? Avatar} alt="user" height={40} style={{ borderRadius: '50%', marginRight: 4 }} />
                    <strong className="ml-2" style={{ fontSize: 16 }}>{message?.user?.fullName}</strong>
                </Col>
                <Col xs={3}></Col>
                <Col style={{ textAlign: 'right', fontSize: 12, color: '#272727' }}>{printTime(message)}</Col>
                <Col xs={12} style={{ marginTop: 8, fontSize: 12 }}>
                    {message?.comment}
                </Col>
                <Col xs={12} style={{ marginTop: 8, marginBottom: 8 }}>
                    {message?.responseId === 0 && <Button variant="primary" size="sm" onClick={() => { setOpenInput(true) }}>Responder</Button>}
                    <span style={{ marginLeft: 8 }}>{`${message?.responses?.length ?? 0} Respuestas`}</span>
                    {
                        openInput && <Row style={{ marginTop: 8 }}>
                            <Col xs={12}>
                                <ResponseForm message={message} messageParentId={messageParentId} userId={userId} getMessages={getMessages.bind(this)} />
                            </Col>
                        </Row>
                    }
                </Col>
            </Row>
        </Col>
        {message?.responses && message.responses.map(response =>
            <div key={response?.id} style={{ paddingLeft: 32 }}>
                <Message message={response} messageParentId={messageParentId} userId={userId} photoUrl={photoUrl} getMessages={getMessages.bind(this)} />
            </div>)}
    </>;
};

export default Message;