package com.tryhard.mvp.app.feed;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import com.tryhard.mvp.app.R;
import com.tryhard.mvp.app.structs.Tweet;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by juancarlos on 09/10/14.
 */
public class FeederFragment extends Fragment {
    public FeederFragment() {}
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String json = "{\n" +
                "    \"statuses\": [\n" +
                "        {\n" +
                "            \"metadata\": {\n" +
                "                \"iso_language_code\": \"es\",\n" +
                "                \"result_type\": \"recent\"\n" +
                "            },\n" +
                "            \"created_at\": \"Thu Oct 09 17:08:31 +0000 2014\",\n" +
                "            \"id\": 520259526780784640,\n" +
                "            \"id_str\": \"520259526780784644\",\n" +
                "            \"text\": \"#Protransporte: Cobro con tarjetas en #CorredorAzul se iniciará en 2015 ► http://t.co/dOYk8rUFws http://t.co/7LSbWu1MRX\",\n" +
                "            \"truncated\": false,\n" +
                "            \"in_reply_to_status_id\": null,\n" +
                "            \"in_reply_to_status_id_str\": null,\n" +
                "            \"in_reply_to_user_id\": null,\n" +
                "            \"in_reply_to_user_id_str\": null,\n" +
                "            \"in_reply_to_screen_name\": null,\n" +
                "            \"user\": {\n" +
                "                \"id\": 1927359882,\n" +
                "                \"id_str\": \"1927359882\",\n" +
                "                \"name\": \"Informate.pe\",\n" +
                "                \"screen_name\": \"informate_pe\",\n" +
                "                \"location\": \"Lima, Perú\",\n" +
                "                \"description\": \"Contáctanos en: http://t.co/RRL4edLoi0\",\n" +
                "                \"url\": \"http://t.co/V4Vvs69lbA\",\n" +
                "                \"entities\": {\n" +
                "                    \"url\": {\n" +
                "                        \"urls\": [\n" +
                "                            {\n" +
                "                                \"url\": \"http://t.co/V4Vvs69lbA\",\n" +
                "                                \"expanded_url\": \"http://www.informate.pe\",\n" +
                "                                \"display_url\": \"informate.pe\",\n" +
                "                                \"indices\": [\n" +
                "                                    0,\n" +
                "                                    22\n" +
                "                                ]\n" +
                "                            }\n" +
                "                        ]\n" +
                "                    },\n" +
                "                    \"description\": {\n" +
                "                        \"urls\": [\n" +
                "                            {\n" +
                "                                \"url\": \"http://t.co/RRL4edLoi0\",\n" +
                "                                \"expanded_url\": \"http://facebook.com/peru.informate\",\n" +
                "                                \"display_url\": \"facebook.com/peru.informate\",\n" +
                "                                \"indices\": [\n" +
                "                                    16,\n" +
                "                                    38\n" +
                "                                ]\n" +
                "                            }\n" +
                "                        ]\n" +
                "                    }\n" +
                "                },\n" +
                "                \"protected\": false,\n" +
                "                \"followers_count\": 461,\n" +
                "                \"friends_count\": 235,\n" +
                "                \"listed_count\": 8,\n" +
                "                \"created_at\": \"Wed Oct 02 16:18:57 +0000 2013\",\n" +
                "                \"favourites_count\": 23,\n" +
                "                \"utc_offset\": null,\n" +
                "                \"time_zone\": null,\n" +
                "                \"geo_enabled\": true,\n" +
                "                \"verified\": false,\n" +
                "                \"statuses_count\": 13283,\n" +
                "                \"lang\": \"es\",\n" +
                "                \"contributors_enabled\": false,\n" +
                "                \"is_translator\": false,\n" +
                "                \"is_translation_enabled\": false,\n" +
                "                \"profile_background_color\": \"FFFFFF\",\n" +
                "                \"profile_background_image_url\": \"http://pbs.twimg.com/profile_background_images/438806099743887360/DGMT-xyM.png\",\n" +
                "                \"profile_background_image_url_https\": \"https://pbs.twimg.com/profile_background_images/438806099743887360/DGMT-xyM.png\",\n" +
                "                \"profile_background_tile\": true,\n" +
                "                \"profile_image_url\": \"http://pbs.twimg.com/profile_images/497812607550492672/BIYcktX__normal.png\",\n" +
                "                \"profile_image_url_https\": \"https://pbs.twimg.com/profile_images/497812607550492672/BIYcktX__normal.png\",\n" +
                "                \"profile_banner_url\": \"https://pbs.twimg.com/profile_banners/1927359882/1408564120\",\n" +
                "                \"profile_link_color\": \"DD2E44\",\n" +
                "                \"profile_sidebar_border_color\": \"FFFFFF\",\n" +
                "                \"profile_sidebar_fill_color\": \"DDEEF6\",\n" +
                "                \"profile_text_color\": \"333333\",\n" +
                "                \"profile_use_background_image\": true,\n" +
                "                \"default_profile\": false,\n" +
                "                \"default_profile_image\": false,\n" +
                "                \"following\": false,\n" +
                "                \"follow_request_sent\": false,\n" +
                "                \"notifications\": false\n" +
                "            },\n" +
                "            \"geo\": null,\n" +
                "            \"coordinates\": null,\n" +
                "            \"place\": null,\n" +
                "            \"contributors\": null,\n" +
                "            \"retweet_count\": 0,\n" +
                "            \"favorite_count\": 0,\n" +
                "            \"entities\": {\n" +
                "                \"hashtags\": [\n" +
                "                    {\n" +
                "                        \"text\": \"Protransporte\",\n" +
                "                        \"indices\": [\n" +
                "                            0,\n" +
                "                            14\n" +
                "                        ]\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"text\": \"CorredorAzul\",\n" +
                "                        \"indices\": [\n" +
                "                            38,\n" +
                "                            51\n" +
                "                        ]\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"symbols\": [],\n" +
                "                \"urls\": [\n" +
                "                    {\n" +
                "                        \"url\": \"http://t.co/dOYk8rUFws\",\n" +
                "                        \"expanded_url\": \"http://goo.gl/6Hqpsp\",\n" +
                "                        \"display_url\": \"goo.gl/6Hqpsp\",\n" +
                "                        \"indices\": [\n" +
                "                            74,\n" +
                "                            96\n" +
                "                        ]\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"user_mentions\": [],\n" +
                "                \"media\": [\n" +
                "                    {\n" +
                "                        \"id\": 520259525262450700,\n" +
                "                        \"id_str\": \"520259525262450688\",\n" +
                "                        \"indices\": [\n" +
                "                            97,\n" +
                "                            119\n" +
                "                        ],\n" +
                "                        \"media_url\": \"http://pbs.twimg.com/media/BzhVSCJIIAAIIRj.jpg\",\n" +
                "                        \"media_url_https\": \"https://pbs.twimg.com/media/BzhVSCJIIAAIIRj.jpg\",\n" +
                "                        \"url\": \"http://t.co/7LSbWu1MRX\",\n" +
                "                        \"display_url\": \"pic.twitter.com/7LSbWu1MRX\",\n" +
                "                        \"expanded_url\": \"http://twitter.com/informate_pe/status/520259526780784644/photo/1\",\n" +
                "                        \"type\": \"photo\",\n" +
                "                        \"sizes\": {\n" +
                "                            \"thumb\": {\n" +
                "                                \"w\": 150,\n" +
                "                                \"h\": 150,\n" +
                "                                \"resize\": \"crop\"\n" +
                "                            },\n" +
                "                            \"small\": {\n" +
                "                                \"w\": 340,\n" +
                "                                \"h\": 190,\n" +
                "                                \"resize\": \"fit\"\n" +
                "                            },\n" +
                "                            \"medium\": {\n" +
                "                                \"w\": 600,\n" +
                "                                \"h\": 336,\n" +
                "                                \"resize\": \"fit\"\n" +
                "                            },\n" +
                "                            \"large\": {\n" +
                "                                \"w\": 619,\n" +
                "                                \"h\": 347,\n" +
                "                                \"resize\": \"fit\"\n" +
                "                            }\n" +
                "                        }\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            \"favorited\": false,\n" +
                "            \"retweeted\": false,\n" +
                "            \"possibly_sensitive\": false,\n" +
                "            \"lang\": \"es\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"metadata\": {\n" +
                "                \"iso_language_code\": \"es\",\n" +
                "                \"result_type\": \"recent\"\n" +
                "            },\n" +
                "            \"created_at\": \"Thu Oct 09 16:50:15 +0000 2014\",\n" +
                "            \"id\": 520254930423398400,\n" +
                "            \"id_str\": \"520254930423398400\",\n" +
                "            \"text\": \"RT @Agencia_Andina: Cobro con tarjetas en #CorredorAzul se iniciará a mediados del año 2015 http://t.co/MuKm1SoPRF http://t.co/VJYG6Dv4d4\",\n" +
                "            \"truncated\": false,\n" +
                "            \"in_reply_to_status_id\": null,\n" +
                "            \"in_reply_to_status_id_str\": null,\n" +
                "            \"in_reply_to_user_id\": null,\n" +
                "            \"in_reply_to_user_id_str\": null,\n" +
                "            \"in_reply_to_screen_name\": null,\n" +
                "            \"user\": {\n" +
                "                \"id\": 299036988,\n" +
                "                \"id_str\": \"299036988\",\n" +
                "                \"name\": \"FRIDA VICUNA \",\n" +
                "                \"screen_name\": \"frivicu\",\n" +
                "                \"location\": \"ITALIA\",\n" +
                "                \"description\": \"PROFESORA DE MATEMATICA - FISICA\",\n" +
                "                \"url\": \"http://t.co/imD3NlgSGu\",\n" +
                "                \"entities\": {\n" +
                "                    \"url\": {\n" +
                "                        \"urls\": [\n" +
                "                            {\n" +
                "                                \"url\": \"http://t.co/imD3NlgSGu\",\n" +
                "                                \"expanded_url\": \"http://twitter.com/frivicu\",\n" +
                "                                \"display_url\": \"twitter.com/frivicu\",\n" +
                "                                \"indices\": [\n" +
                "                                    0,\n" +
                "                                    22\n" +
                "                                ]\n" +
                "                            }\n" +
                "                        ]\n" +
                "                    },\n" +
                "                    \"description\": {\n" +
                "                        \"urls\": []\n" +
                "                    }\n" +
                "                },\n" +
                "                \"protected\": false,\n" +
                "                \"followers_count\": 89,\n" +
                "                \"friends_count\": 339,\n" +
                "                \"listed_count\": 0,\n" +
                "                \"created_at\": \"Sun May 15 11:50:18 +0000 2011\",\n" +
                "                \"favourites_count\": 704,\n" +
                "                \"utc_offset\": 10800,\n" +
                "                \"time_zone\": \"Athens\",\n" +
                "                \"geo_enabled\": true,\n" +
                "                \"verified\": false,\n" +
                "                \"statuses_count\": 3038,\n" +
                "                \"lang\": \"es\",\n" +
                "                \"contributors_enabled\": false,\n" +
                "                \"is_translator\": false,\n" +
                "                \"is_translation_enabled\": false,\n" +
                "                \"profile_background_color\": \"C0DEED\",\n" +
                "                \"profile_background_image_url\": \"http://abs.twimg.com/images/themes/theme1/bg.png\",\n" +
                "                \"profile_background_image_url_https\": \"https://abs.twimg.com/images/themes/theme1/bg.png\",\n" +
                "                \"profile_background_tile\": false,\n" +
                "                \"profile_image_url\": \"http://pbs.twimg.com/profile_images/517069504778481664/vbvOXvWl_normal.jpeg\",\n" +
                "                \"profile_image_url_https\": \"https://pbs.twimg.com/profile_images/517069504778481664/vbvOXvWl_normal.jpeg\",\n" +
                "                \"profile_banner_url\": \"https://pbs.twimg.com/profile_banners/299036988/1412114115\",\n" +
                "                \"profile_link_color\": \"5FA657\",\n" +
                "                \"profile_sidebar_border_color\": \"FFFFFF\",\n" +
                "                \"profile_sidebar_fill_color\": \"DDEEF6\",\n" +
                "                \"profile_text_color\": \"333333\",\n" +
                "                \"profile_use_background_image\": true,\n" +
                "                \"default_profile\": false,\n" +
                "                \"default_profile_image\": false,\n" +
                "                \"following\": false,\n" +
                "                \"follow_request_sent\": false,\n" +
                "                \"notifications\": false\n" +
                "            },\n" +
                "            \"geo\": null,\n" +
                "            \"coordinates\": null,\n" +
                "            \"place\": null,\n" +
                "            \"contributors\": null,\n" +
                "            \"retweeted_status\": {\n" +
                "                \"metadata\": {\n" +
                "                    \"iso_language_code\": \"es\",\n" +
                "                    \"result_type\": \"recent\"\n" +
                "                },\n" +
                "                \"created_at\": \"Thu Oct 09 16:30:40 +0000 2014\",\n" +
                "                \"id\": 520250002338484200,\n" +
                "                \"id_str\": \"520250002338484225\",\n" +
                "                \"text\": \"Cobro con tarjetas en #CorredorAzul se iniciará a mediados del año 2015 http://t.co/MuKm1SoPRF http://t.co/VJYG6Dv4d4\",\n" +
                "                \"truncated\": false,\n" +
                "                \"in_reply_to_status_id\": null,\n" +
                "                \"in_reply_to_status_id_str\": null,\n" +
                "                \"in_reply_to_user_id\": null,\n" +
                "                \"in_reply_to_user_id_str\": null,\n" +
                "                \"in_reply_to_screen_name\": null,\n" +
                "                \"user\": {\n" +
                "                    \"id\": 40933920,\n" +
                "                    \"id_str\": \"40933920\",\n" +
                "                    \"name\": \"Agencia Andina\",\n" +
                "                    \"screen_name\": \"Agencia_Andina\",\n" +
                "                    \"location\": \"Lima\",\n" +
                "                    \"description\": \"Agencia Oficial de Noticias del Estado Peruano\",\n" +
                "                    \"url\": \"http://t.co/JA7pxdaa6n\",\n" +
                "                    \"entities\": {\n" +
                "                        \"url\": {\n" +
                "                            \"urls\": [\n" +
                "                                {\n" +
                "                                    \"url\": \"http://t.co/JA7pxdaa6n\",\n" +
                "                                    \"expanded_url\": \"http://www.andina.com.pe/\",\n" +
                "                                    \"display_url\": \"andina.com.pe\",\n" +
                "                                    \"indices\": [\n" +
                "                                        0,\n" +
                "                                        22\n" +
                "                                    ]\n" +
                "                                }\n" +
                "                            ]\n" +
                "                        },\n" +
                "                        \"description\": {\n" +
                "                            \"urls\": []\n" +
                "                        }\n" +
                "                    },\n" +
                "                    \"protected\": false,\n" +
                "                    \"followers_count\": 171910,\n" +
                "                    \"friends_count\": 1440,\n" +
                "                    \"listed_count\": 1412,\n" +
                "                    \"created_at\": \"Mon May 18 18:31:50 +0000 2009\",\n" +
                "                    \"favourites_count\": 86,\n" +
                "                    \"utc_offset\": -18000,\n" +
                "                    \"time_zone\": \"Lima\",\n" +
                "                    \"geo_enabled\": false,\n" +
                "                    \"verified\": false,\n" +
                "                    \"statuses_count\": 114483,\n" +
                "                    \"lang\": \"es\",\n" +
                "                    \"contributors_enabled\": false,\n" +
                "                    \"is_translator\": false,\n" +
                "                    \"is_translation_enabled\": false,\n" +
                "                    \"profile_background_color\": \"FFFFFF\",\n" +
                "                    \"profile_background_image_url\": \"http://pbs.twimg.com/profile_background_images/431542694419001344/d4vHIne-.jpeg\",\n" +
                "                    \"profile_background_image_url_https\": \"https://pbs.twimg.com/profile_background_images/431542694419001344/d4vHIne-.jpeg\",\n" +
                "                    \"profile_background_tile\": true,\n" +
                "                    \"profile_image_url\": \"http://pbs.twimg.com/profile_images/495251719924637696/bOgd_ER5_normal.jpeg\",\n" +
                "                    \"profile_image_url_https\": \"https://pbs.twimg.com/profile_images/495251719924637696/bOgd_ER5_normal.jpeg\",\n" +
                "                    \"profile_banner_url\": \"https://pbs.twimg.com/profile_banners/40933920/1411134674\",\n" +
                "                    \"profile_link_color\": \"DF111B\",\n" +
                "                    \"profile_sidebar_border_color\": \"FFFFFF\",\n" +
                "                    \"profile_sidebar_fill_color\": \"C9C9C9\",\n" +
                "                    \"profile_text_color\": \"000000\",\n" +
                "                    \"profile_use_background_image\": true,\n" +
                "                    \"default_profile\": false,\n" +
                "                    \"default_profile_image\": false,\n" +
                "                    \"following\": false,\n" +
                "                    \"follow_request_sent\": false,\n" +
                "                    \"notifications\": false\n" +
                "                },\n" +
                "                \"geo\": null,\n" +
                "                \"coordinates\": null,\n" +
                "                \"place\": null,\n" +
                "                \"contributors\": null,\n" +
                "                \"retweet_count\": 4,\n" +
                "                \"favorite_count\": 1,\n" +
                "                \"entities\": {\n" +
                "                    \"hashtags\": [\n" +
                "                        {\n" +
                "                            \"text\": \"CorredorAzul\",\n" +
                "                            \"indices\": [\n" +
                "                                22,\n" +
                "                                35\n" +
                "                            ]\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"symbols\": [],\n" +
                "                    \"urls\": [\n" +
                "                        {\n" +
                "                            \"url\": \"http://t.co/MuKm1SoPRF\",\n" +
                "                            \"expanded_url\": \"http://bit.ly/1yQhnY6\",\n" +
                "                            \"display_url\": \"bit.ly/1yQhnY6\",\n" +
                "                            \"indices\": [\n" +
                "                                72,\n" +
                "                                94\n" +
                "                            ]\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"user_mentions\": [],\n" +
                "                    \"media\": [\n" +
                "                        {\n" +
                "                            \"id\": 520250000870481900,\n" +
                "                            \"id_str\": \"520250000870481920\",\n" +
                "                            \"indices\": [\n" +
                "                                95,\n" +
                "                                117\n" +
                "                            ],\n" +
                "                            \"media_url\": \"http://pbs.twimg.com/media/BzhMnpBIIAAnbp-.png\",\n" +
                "                            \"media_url_https\": \"https://pbs.twimg.com/media/BzhMnpBIIAAnbp-.png\",\n" +
                "                            \"url\": \"http://t.co/VJYG6Dv4d4\",\n" +
                "                            \"display_url\": \"pic.twitter.com/VJYG6Dv4d4\",\n" +
                "                            \"expanded_url\": \"http://twitter.com/Agencia_Andina/status/520250002338484225/photo/1\",\n" +
                "                            \"type\": \"photo\",\n" +
                "                            \"sizes\": {\n" +
                "                                \"medium\": {\n" +
                "                                    \"w\": 600,\n" +
                "                                    \"h\": 362,\n" +
                "                                    \"resize\": \"fit\"\n" +
                "                                },\n" +
                "                                \"thumb\": {\n" +
                "                                    \"w\": 150,\n" +
                "                                    \"h\": 150,\n" +
                "                                    \"resize\": \"crop\"\n" +
                "                                },\n" +
                "                                \"small\": {\n" +
                "                                    \"w\": 340,\n" +
                "                                    \"h\": 205,\n" +
                "                                    \"resize\": \"fit\"\n" +
                "                                },\n" +
                "                                \"large\": {\n" +
                "                                    \"w\": 696,\n" +
                "                                    \"h\": 421,\n" +
                "                                    \"resize\": \"fit\"\n" +
                "                                }\n" +
                "                            }\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                \"favorited\": false,\n" +
                "                \"retweeted\": false,\n" +
                "                \"possibly_sensitive\": false,\n" +
                "                \"lang\": \"es\"\n" +
                "            },\n" +
                "            \"retweet_count\": 4,\n" +
                "            \"favorite_count\": 0,\n" +
                "            \"entities\": {\n" +
                "                \"hashtags\": [\n" +
                "                    {\n" +
                "                        \"text\": \"CorredorAzul\",\n" +
                "                        \"indices\": [\n" +
                "                            42,\n" +
                "                            55\n" +
                "                        ]\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"symbols\": [],\n" +
                "                \"urls\": [\n" +
                "                    {\n" +
                "                        \"url\": \"http://t.co/MuKm1SoPRF\",\n" +
                "                        \"expanded_url\": \"http://bit.ly/1yQhnY6\",\n" +
                "                        \"display_url\": \"bit.ly/1yQhnY6\",\n" +
                "                        \"indices\": [\n" +
                "                            92,\n" +
                "                            114\n" +
                "                        ]\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"user_mentions\": [\n" +
                "                    {\n" +
                "                        \"screen_name\": \"Agencia_Andina\",\n" +
                "                        \"name\": \"Agencia Andina\",\n" +
                "                        \"id\": 40933920,\n" +
                "                        \"id_str\": \"40933920\",\n" +
                "                        \"indices\": [\n" +
                "                            3,\n" +
                "                            18\n" +
                "                        ]\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"media\": [\n" +
                "                    {\n" +
                "                        \"id\": 520250000870481900,\n" +
                "                        \"id_str\": \"520250000870481920\",\n" +
                "                        \"indices\": [\n" +
                "                            115,\n" +
                "                            137\n" +
                "                        ],\n" +
                "                        \"media_url\": \"http://pbs.twimg.com/media/BzhMnpBIIAAnbp-.png\",\n" +
                "                        \"media_url_https\": \"https://pbs.twimg.com/media/BzhMnpBIIAAnbp-.png\",\n" +
                "                        \"url\": \"http://t.co/VJYG6Dv4d4\",\n" +
                "                        \"display_url\": \"pic.twitter.com/VJYG6Dv4d4\",\n" +
                "                        \"expanded_url\": \"http://twitter.com/Agencia_Andina/status/520250002338484225/photo/1\",\n" +
                "                        \"type\": \"photo\",\n" +
                "                        \"sizes\": {\n" +
                "                            \"medium\": {\n" +
                "                                \"w\": 600,\n" +
                "                                \"h\": 362,\n" +
                "                                \"resize\": \"fit\"\n" +
                "                            },\n" +
                "                            \"thumb\": {\n" +
                "                                \"w\": 150,\n" +
                "                                \"h\": 150,\n" +
                "                                \"resize\": \"crop\"\n" +
                "                            },\n" +
                "                            \"small\": {\n" +
                "                                \"w\": 340,\n" +
                "                                \"h\": 205,\n" +
                "                                \"resize\": \"fit\"\n" +
                "                            },\n" +
                "                            \"large\": {\n" +
                "                                \"w\": 696,\n" +
                "                                \"h\": 421,\n" +
                "                                \"resize\": \"fit\"\n" +
                "                            }\n" +
                "                        },\n" +
                "                        \"source_status_id\": 520250002338484200,\n" +
                "                        \"source_status_id_str\": \"520250002338484225\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            \"favorited\": false,\n" +
                "            \"retweeted\": false,\n" +
                "            \"possibly_sensitive\": false,\n" +
                "            \"lang\": \"es\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"search_metadata\": {\n" +
                "        \"completed_in\": 0.016,\n" +
                "        \"max_id\": 520259526780784640,\n" +
                "        \"max_id_str\": \"520259526780784644\",\n" +
                "        \"next_results\": \"?max_id=520254930423398399&q=corredorAzul&count=2&include_entities=1\",\n" +
                "        \"query\": \"corredorAzul\",\n" +
                "        \"refresh_url\": \"?since_id=520259526780784644&q=corredorAzul&include_entities=1\",\n" +
                "        \"count\": 2,\n" +
                "        \"since_id\": 0,\n" +
                "        \"since_id_str\": \"0\"\n" +
                "    }\n" +
                "}";
        getLastTweets(json);
        View v = inflater.inflate(R.layout.feeder_fragment, container, false);
        Context ctx = container.getContext();
        ListView list = (ListView) v.findViewById(R.id.list_feeder);
        String[] data = {"Gg", "Hh", "Ff"};
        list.setAdapter(new TweetAdapter(ctx, getLastTweets(6)));
        return v;
    }

    public ArrayList<Tweet> getLastTweets(int number) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(number);
        tweets.add(0,new Tweet("Redaccion Peru", "#CorredorAzul: Cobro de pasajes en buses se iniciará el 15 de octubre http://goo.gl/MitMgF", "1h"));
        tweets.add(1,new Tweet("Radio Exitosa", " Hay patios (en el #CorredorAzul). Los buses duermen en patios ubicados en #SJL y en #Chorrillos", "1h"));
        tweets.add(2,new Tweet("Redaccion Peru", "#CorredorAzul: Cobro de pasajes en buses se iniciará el 15 de octubre http://goo.gl/MitMgF", "1h"));
        tweets.add(3,new Tweet("Redaccion Peru", "#CorredorAzul: Cobro de pasajes en buses se iniciará el 15 de octubre http://goo.gl/MitMgF", "1h"));
        tweets.add(4,new Tweet("Redaccion Peru", "#CorredorAzul: Cobro de pasajes en buses se iniciará el 15 de octubre http://goo.gl/MitMgF", "1h"));
        tweets.add(5,new Tweet("Redaccion Peru", "#CorredorAzul: Cobro de pasajes en buses se iniciará el 15 de octubre http://goo.gl/MitMgF", "1h"));
        return tweets;

    }
    public void getLastTweets(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonNode idNode = rootNode.path("statuses");
        for (JsonNode node : rootNode.path("statuses")) {
            //Log.d("TEST", node.path("text").toString()+ node.path("name").toString());
        }

        /*Iterator<Map.Entry<String,JsonNode>> fieldsIterator = rootNode.getFields();
        while (fieldsIterator.hasNext()) {

            Map.Entry<String,JsonNode> field = fieldsIterator.next();
            Log.d("TEST", "Key:" + field.getKey() + "\tValue:" + field.getValue());
        }*/
    }


}
