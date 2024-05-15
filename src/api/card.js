import request from '@/utils/request'

export const getAllCards = () => 
    request.get(`/cards`)

export const addCard = (cardData) => 
    request.post(`/cards/add`, cardData);

export const deleteCard = (id) => 
    request.delete(`/cards/delete/${id}`);

export const updateCard = (id, cardData) => 
    request.put(`/cards/edit/${id}`, cardData);
