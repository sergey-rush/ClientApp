package ru.customservice;

import ru.customservice.Post;

interface IPostService
{
    Post getLastPost();
    void exit();
}