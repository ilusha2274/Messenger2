--
-- PostgreSQL database dump
--

-- Dumped from database version 14.0
-- Dumped by pg_dump version 14.0

-- Started on 2021-11-07 16:40:48

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 210 (class 1259 OID 16464)
-- Name: chats; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chats (
    chat_id integer NOT NULL,
    chat_last_message integer,
    user1_id integer NOT NULL,
    user2_id integer NOT NULL
);


ALTER TABLE public.chats OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 16486)
-- Name: chats_chat_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.chats ALTER COLUMN chat_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.chats_chat_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 211 (class 1259 OID 16471)
-- Name: messages; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.messages (
    message_id integer NOT NULL,
    text_message text NOT NULL,
    chat_id integer NOT NULL,
    user_id integer NOT NULL,
    date_message timestamp with time zone
);


ALTER TABLE public.messages OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16484)
-- Name: messages_message_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.messages ALTER COLUMN message_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.messages_message_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 209 (class 1259 OID 16459)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    user_name character varying(20) NOT NULL,
    user_email character varying(30) NOT NULL,
    user_password character varying(20) NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16540)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.users ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.users_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 3325 (class 0 OID 16464)
-- Dependencies: 210
-- Data for Name: chats; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.chats (chat_id, chat_last_message, user1_id, user2_id) OVERRIDING SYSTEM VALUE VALUES (8, 13, 3, 4);
INSERT INTO public.chats (chat_id, chat_last_message, user1_id, user2_id) OVERRIDING SYSTEM VALUE VALUES (12, 14, 6, 4);


--
-- TOC entry 3326 (class 0 OID 16471)
-- Dependencies: 211
-- Data for Name: messages; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.messages (message_id, text_message, chat_id, user_id, date_message) OVERRIDING SYSTEM VALUE VALUES (11, 'hi', 8, 3, '2021-11-07 15:02:37.498076+03');
INSERT INTO public.messages (message_id, text_message, chat_id, user_id, date_message) OVERRIDING SYSTEM VALUE VALUES (12, 'hi 2', 8, 4, '2021-11-07 15:03:58.84505+03');
INSERT INTO public.messages (message_id, text_message, chat_id, user_id, date_message) OVERRIDING SYSTEM VALUE VALUES (13, '123456', 8, 3, '2021-11-07 15:07:46.264344+03');
INSERT INTO public.messages (message_id, text_message, chat_id, user_id, date_message) OVERRIDING SYSTEM VALUE VALUES (14, 'h1', 12, 6, '2021-11-07 16:34:07.185521+03');


--
-- TOC entry 3324 (class 0 OID 16459)
-- Dependencies: 209
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users (user_id, user_name, user_email, user_password) OVERRIDING SYSTEM VALUE VALUES (3, '123', '123', '123');
INSERT INTO public.users (user_id, user_name, user_email, user_password) OVERRIDING SYSTEM VALUE VALUES (4, '111', '111', '111');
INSERT INTO public.users (user_id, user_name, user_email, user_password) OVERRIDING SYSTEM VALUE VALUES (6, '333', '333', '333');


--
-- TOC entry 3335 (class 0 OID 0)
-- Dependencies: 213
-- Name: chats_chat_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.chats_chat_id_seq', 12, true);


--
-- TOC entry 3336 (class 0 OID 0)
-- Dependencies: 212
-- Name: messages_message_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.messages_message_id_seq', 14, true);


--
-- TOC entry 3337 (class 0 OID 0)
-- Dependencies: 214
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 6, true);


--
-- TOC entry 3177 (class 2606 OID 16470)
-- Name: chats chats_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chats
    ADD CONSTRAINT chats_pkey PRIMARY KEY (chat_id);


--
-- TOC entry 3179 (class 2606 OID 16477)
-- Name: messages messages_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_pkey PRIMARY KEY (message_id);


--
-- TOC entry 3175 (class 2606 OID 16463)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 3183 (class 2606 OID 16478)
-- Name: messages chat_id_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT chat_id_key FOREIGN KEY (chat_id) REFERENCES public.chats(chat_id) NOT VALID;


--
-- TOC entry 3180 (class 2606 OID 16541)
-- Name: chats chats_chat_last_message_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chats
    ADD CONSTRAINT chats_chat_last_message_fkey FOREIGN KEY (chat_last_message) REFERENCES public.messages(message_id) NOT VALID;


--
-- TOC entry 3181 (class 2606 OID 16617)
-- Name: chats user1_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chats
    ADD CONSTRAINT user1_fkey FOREIGN KEY (user1_id) REFERENCES public.users(user_id) NOT VALID;


--
-- TOC entry 3182 (class 2606 OID 16622)
-- Name: chats user2_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chats
    ADD CONSTRAINT user2_fkey FOREIGN KEY (user2_id) REFERENCES public.users(user_id) NOT VALID;


--
-- TOC entry 3184 (class 2606 OID 16487)
-- Name: messages user_id_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT user_id_key FOREIGN KEY (user_id) REFERENCES public.users(user_id) NOT VALID;


-- Completed on 2021-11-07 16:40:48

--
-- PostgreSQL database dump complete
--

