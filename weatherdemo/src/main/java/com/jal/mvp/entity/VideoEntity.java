package com.jal.mvp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SEELE on 2017/3/14.
 */

public class VideoEntity implements Serializable {
    private int count;
    private int total;
    private Object nextPageUrl;
    private List<ItemListBeanX> itemList;

    @Override
    public String toString() {
        return "VideoEntity{" +
                "count=" + count +
                ", total=" + total +
                ", nextPageUrl=" + nextPageUrl +
                ", itemList=" + itemList +
                '}';
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(Object nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public List<ItemListBeanX> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemListBeanX> itemList) {
        this.itemList = itemList;
    }

    public static class ItemListBeanX implements Serializable{
        @Override
        public String toString() {
            return "ItemListBeanX{" +
                    "type='" + type + '\'' +
                    ", data=" + data +
                    ", tag=" + tag +
                    '}';
        }

        private String type;
        private DataBeanX data;
        private Object tag;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public DataBeanX getData() {
            return data;
        }

        public void setData(DataBeanX data) {
            this.data = data;
        }

        public Object getTag() {
            return tag;
        }

        public void setTag(Object tag) {
            this.tag = tag;
        }

        public static class DataBeanX  implements Serializable{
            @Override
            public String toString() {
                return "DataBeanX{" +
                        "dataType='" + dataType + '\'' +
                        ", header=" + header +
                        ", count=" + count +
                        ", adTrack=" + adTrack +
                        ", itemList=" + itemList +
                        '}';
            }

            private String dataType;
            private HeaderBean header;
            private int count;
            private Object adTrack;
            private List<ItemListBean> itemList;

            public String getDataType() {
                return dataType;
            }

            public void setDataType(String dataType) {
                this.dataType = dataType;
            }

            public HeaderBean getHeader() {
                return header;
            }

            public void setHeader(HeaderBean header) {
                this.header = header;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public Object getAdTrack() {
                return adTrack;
            }

            public void setAdTrack(Object adTrack) {
                this.adTrack = adTrack;
            }

            public List<ItemListBean> getItemList() {
                return itemList;
            }

            public void setItemList(List<ItemListBean> itemList) {
                this.itemList = itemList;
            }

            public static class HeaderBean implements Serializable{
                @Override
                public String toString() {
                    return "HeaderBean{" +
                            "id=" + id +
                            ", title='" + title + '\'' +
                            ", font='" + font + '\'' +
                            ", cover=" + cover +
                            ", label=" + label +
                            ", actionUrl='" + actionUrl + '\'' +
                            '}';
                }

                /**
                 * id : 140
                 * title : 搞笑相关视频
                 * font : normal
                 * cover : null
                 * label : null
                 * actionUrl : eyepetizer://tag/140/?title=%E6%90%9E%E7%AC%91
                 */


                private int id;
                private String title;
                private String font;
                private Object cover;
                private Object label;
                private String actionUrl;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getFont() {
                    return font;
                }

                public void setFont(String font) {
                    this.font = font;
                }

                public Object getCover() {
                    return cover;
                }

                public void setCover(Object cover) {
                    this.cover = cover;
                }

                public Object getLabel() {
                    return label;
                }

                public void setLabel(Object label) {
                    this.label = label;
                }

                public String getActionUrl() {
                    return actionUrl;
                }

                public void setActionUrl(String actionUrl) {
                    this.actionUrl = actionUrl;
                }
            }

            public static class ItemListBean implements Serializable{
                @Override
                public String toString() {
                    return "ItemListBean{" +
                            "type='" + type + '\'' +
                            ", data=" + data +
                            ", tag=" + tag +
                            '}';
                }

                private String type;
                private DataBean data;
                private Object tag;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public DataBean getData() {
                    return data;
                }

                public void setData(DataBean data) {
                    this.data = data;
                }

                public Object getTag() {
                    return tag;
                }

                public void setTag(Object tag) {
                    this.tag = tag;
                }

                public static class DataBean implements Serializable{
                    private String dataType;
                    private int id;
                    private String title;
                    private String description;
                    private ProviderBean provider;
                    private String category;
                    private Object author;
                    private CoverBean cover;
                    private String playUrl;
                    private int duration;
                    private WebUrlBean webUrl;
                    private long releaseTime;
                    private ConsumptionBean consumption;
                    private Object campaign;
                    private Object waterMarks;
                    private Object adTrack;
                    private String type;
                    private Object titlePgc;
                    private Object descriptionPgc;
                    private int idx;
                    private Object shareAdTrack;
                    private Object favoriteAdTrack;
                    private Object webAdTrack;
                    private long date;
                    private Object promotion;
                    private Object label;
                    private String descriptionEditor;
                    private boolean collected;
                    private boolean played;
                    private List<PlayInfoBean> playInfo;
                    private List<TagsBean> tags;
                    private List<?> subtitles;

                    @Override
                    public String toString() {
                        return "DataBean{" +
                                "dataType='" + dataType + '\'' +
                                ", id=" + id +
                                ", title='" + title + '\'' +
                                ", description='" + description + '\'' +
                                ", provider=" + provider +
                                ", category='" + category + '\'' +
                                ", author=" + author +
                                ", cover=" + cover +
                                ", playUrl='" + playUrl + '\'' +
                                ", duration=" + duration +
                                ", webUrl=" + webUrl +
                                ", releaseTime=" + releaseTime +
                                ", consumption=" + consumption +
                                ", campaign=" + campaign +
                                ", waterMarks=" + waterMarks +
                                ", adTrack=" + adTrack +
                                ", type='" + type + '\'' +
                                ", titlePgc=" + titlePgc +
                                ", descriptionPgc=" + descriptionPgc +
                                ", idx=" + idx +
                                ", shareAdTrack=" + shareAdTrack +
                                ", favoriteAdTrack=" + favoriteAdTrack +
                                ", webAdTrack=" + webAdTrack +
                                ", date=" + date +
                                ", promotion=" + promotion +
                                ", label=" + label +
                                ", descriptionEditor='" + descriptionEditor + '\'' +
                                ", collected=" + collected +
                                ", played=" + played +
                                ", playInfo=" + playInfo +
                                ", tags=" + tags +
                                ", subtitles=" + subtitles +
                                '}';
                    }

                    public String getDataType() {
                        return dataType;
                    }

                    public void setDataType(String dataType) {
                        this.dataType = dataType;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public ProviderBean getProvider() {
                        return provider;
                    }

                    public void setProvider(ProviderBean provider) {
                        this.provider = provider;
                    }

                    public String getCategory() {
                        return category;
                    }

                    public void setCategory(String category) {
                        this.category = category;
                    }

                    public Object getAuthor() {
                        return author;
                    }

                    public void setAuthor(Object author) {
                        this.author = author;
                    }

                    public CoverBean getCover() {
                        return cover;
                    }

                    public void setCover(CoverBean cover) {
                        this.cover = cover;
                    }

                    public String getPlayUrl() {
                        return playUrl;
                    }

                    public void setPlayUrl(String playUrl) {
                        this.playUrl = playUrl;
                    }

                    public int getDuration() {
                        return duration;
                    }

                    public void setDuration(int duration) {
                        this.duration = duration;
                    }

                    public WebUrlBean getWebUrl() {
                        return webUrl;
                    }

                    public void setWebUrl(WebUrlBean webUrl) {
                        this.webUrl = webUrl;
                    }

                    public long getReleaseTime() {
                        return releaseTime;
                    }

                    public void setReleaseTime(long releaseTime) {
                        this.releaseTime = releaseTime;
                    }

                    public ConsumptionBean getConsumption() {
                        return consumption;
                    }

                    public void setConsumption(ConsumptionBean consumption) {
                        this.consumption = consumption;
                    }

                    public Object getCampaign() {
                        return campaign;
                    }

                    public void setCampaign(Object campaign) {
                        this.campaign = campaign;
                    }

                    public Object getWaterMarks() {
                        return waterMarks;
                    }

                    public void setWaterMarks(Object waterMarks) {
                        this.waterMarks = waterMarks;
                    }

                    public Object getAdTrack() {
                        return adTrack;
                    }

                    public void setAdTrack(Object adTrack) {
                        this.adTrack = adTrack;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public Object getTitlePgc() {
                        return titlePgc;
                    }

                    public void setTitlePgc(Object titlePgc) {
                        this.titlePgc = titlePgc;
                    }

                    public Object getDescriptionPgc() {
                        return descriptionPgc;
                    }

                    public void setDescriptionPgc(Object descriptionPgc) {
                        this.descriptionPgc = descriptionPgc;
                    }

                    public int getIdx() {
                        return idx;
                    }

                    public void setIdx(int idx) {
                        this.idx = idx;
                    }

                    public Object getShareAdTrack() {
                        return shareAdTrack;
                    }

                    public void setShareAdTrack(Object shareAdTrack) {
                        this.shareAdTrack = shareAdTrack;
                    }

                    public Object getFavoriteAdTrack() {
                        return favoriteAdTrack;
                    }

                    public void setFavoriteAdTrack(Object favoriteAdTrack) {
                        this.favoriteAdTrack = favoriteAdTrack;
                    }

                    public Object getWebAdTrack() {
                        return webAdTrack;
                    }

                    public void setWebAdTrack(Object webAdTrack) {
                        this.webAdTrack = webAdTrack;
                    }

                    public long getDate() {
                        return date;
                    }

                    public void setDate(long date) {
                        this.date = date;
                    }

                    public Object getPromotion() {
                        return promotion;
                    }

                    public void setPromotion(Object promotion) {
                        this.promotion = promotion;
                    }

                    public Object getLabel() {
                        return label;
                    }

                    public void setLabel(Object label) {
                        this.label = label;
                    }

                    public String getDescriptionEditor() {
                        return descriptionEditor;
                    }

                    public void setDescriptionEditor(String descriptionEditor) {
                        this.descriptionEditor = descriptionEditor;
                    }

                    public boolean isCollected() {
                        return collected;
                    }

                    public void setCollected(boolean collected) {
                        this.collected = collected;
                    }

                    public boolean isPlayed() {
                        return played;
                    }

                    public void setPlayed(boolean played) {
                        this.played = played;
                    }

                    public List<PlayInfoBean> getPlayInfo() {
                        return playInfo;
                    }

                    public void setPlayInfo(List<PlayInfoBean> playInfo) {
                        this.playInfo = playInfo;
                    }

                    public List<TagsBean> getTags() {
                        return tags;
                    }

                    public void setTags(List<TagsBean> tags) {
                        this.tags = tags;
                    }

                    public List<?> getSubtitles() {
                        return subtitles;
                    }

                    public void setSubtitles(List<?> subtitles) {
                        this.subtitles = subtitles;
                    }

                    public static class ProviderBean implements Serializable{
                        private String name;
                        private String alias;
                        private String icon;

                        @Override
                        public String toString() {
                            return "ProviderBean{" +
                                    "name='" + name + '\'' +
                                    ", alias='" + alias + '\'' +
                                    ", icon='" + icon + '\'' +
                                    '}';
                        }

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public String getAlias() {
                            return alias;
                        }

                        public void setAlias(String alias) {
                            this.alias = alias;
                        }

                        public String getIcon() {
                            return icon;
                        }

                        public void setIcon(String icon) {
                            this.icon = icon;
                        }
                    }

                    public static class CoverBean implements Serializable{
                        private String feed;
                        private String detail;
                        private String blurred;
                        private Object sharing;

                        @Override
                        public String toString() {
                            return "CoverBean{" +
                                    "feed='" + feed + '\'' +
                                    ", detail='" + detail + '\'' +
                                    ", blurred='" + blurred + '\'' +
                                    ", sharing=" + sharing +
                                    '}';
                        }

                        public String getFeed() {
                            return feed;
                        }

                        public void setFeed(String feed) {
                            this.feed = feed;
                        }

                        public String getDetail() {
                            return detail;
                        }

                        public void setDetail(String detail) {
                            this.detail = detail;
                        }

                        public String getBlurred() {
                            return blurred;
                        }

                        public void setBlurred(String blurred) {
                            this.blurred = blurred;
                        }

                        public Object getSharing() {
                            return sharing;
                        }

                        public void setSharing(Object sharing) {
                            this.sharing = sharing;
                        }
                    }

                    public static class WebUrlBean implements Serializable{
                        private String raw;
                        private String forWeibo;

                        @Override
                        public String toString() {
                            return "WebUrlBean{" +
                                    "raw='" + raw + '\'' +
                                    ", forWeibo='" + forWeibo + '\'' +
                                    '}';
                        }

                        public String getRaw() {
                            return raw;
                        }

                        public void setRaw(String raw) {
                            this.raw = raw;
                        }

                        public String getForWeibo() {
                            return forWeibo;
                        }

                        public void setForWeibo(String forWeibo) {
                            this.forWeibo = forWeibo;
                        }
                    }

                    public static class ConsumptionBean implements Serializable{
                        private int collectionCount;
                        private int shareCount;
                        private int replyCount;

                        @Override
                        public String toString() {
                            return "ConsumptionBean{" +
                                    "collectionCount=" + collectionCount +
                                    ", shareCount=" + shareCount +
                                    ", replyCount=" + replyCount +
                                    '}';
                        }

                        public int getCollectionCount() {
                            return collectionCount;
                        }

                        public void setCollectionCount(int collectionCount) {
                            this.collectionCount = collectionCount;
                        }

                        public int getShareCount() {
                            return shareCount;
                        }

                        public void setShareCount(int shareCount) {
                            this.shareCount = shareCount;
                        }

                        public int getReplyCount() {
                            return replyCount;
                        }

                        public void setReplyCount(int replyCount) {
                            this.replyCount = replyCount;
                        }
                    }

                    public static class PlayInfoBean implements Serializable{
                        private int height;
                        private int width;
                        private String name;
                        private String type;
                        private String url;
                        private List<UrlListBean> urlList;

                        @Override
                        public String toString() {
                            return "PlayInfoBean{" +
                                    "height=" + height +
                                    ", width=" + width +
                                    ", name='" + name + '\'' +
                                    ", type='" + type + '\'' +
                                    ", url='" + url + '\'' +
                                    ", urlList=" + urlList +
                                    '}';
                        }

                        public int getHeight() {
                            return height;
                        }

                        public void setHeight(int height) {
                            this.height = height;
                        }

                        public int getWidth() {
                            return width;
                        }

                        public void setWidth(int width) {
                            this.width = width;
                        }

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public String getType() {
                            return type;
                        }

                        public void setType(String type) {
                            this.type = type;
                        }

                        public String getUrl() {
                            return url;
                        }

                        public void setUrl(String url) {
                            this.url = url;
                        }

                        public List<UrlListBean> getUrlList() {
                            return urlList;
                        }

                        public void setUrlList(List<UrlListBean> urlList) {
                            this.urlList = urlList;
                        }

                        public static class UrlListBean implements Serializable{
                            @Override
                            public String toString() {
                                return "UrlListBean{" +
                                        "name='" + name + '\'' +
                                        ", url='" + url + '\'' +
                                        '}';
                            }

                            /**
                             * name : ucloud
                             * url : http://baobab.kaiyanapp.com/api/v1/playUrl?vid=5198&editionType=low&source=ucloud
                             */


                            private String name;
                            private String url;

                            public String getName() {
                                return name;
                            }

                            public void setName(String name) {
                                this.name = name;
                            }

                            public String getUrl() {
                                return url;
                            }

                            public void setUrl(String url) {
                                this.url = url;
                            }
                        }
                    }

                    public static class TagsBean implements Serializable{
                        private int id;
                        private String name;
                        private String actionUrl;
                        private Object adTrack;

                        @Override
                        public String toString() {
                            return "TagsBean{" +
                                    "id=" + id +
                                    ", name='" + name + '\'' +
                                    ", actionUrl='" + actionUrl + '\'' +
                                    ", adTrack=" + adTrack +
                                    '}';
                        }

                        public int getId() {
                            return id;
                        }

                        public void setId(int id) {
                            this.id = id;
                        }

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public String getActionUrl() {
                            return actionUrl;
                        }

                        public void setActionUrl(String actionUrl) {
                            this.actionUrl = actionUrl;
                        }

                        public Object getAdTrack() {
                            return adTrack;
                        }

                        public void setAdTrack(Object adTrack) {
                            this.adTrack = adTrack;
                        }
                    }
                }
            }
        }
    }
}
